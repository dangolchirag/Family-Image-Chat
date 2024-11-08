package com.chat.familyimagechat.feature.presentation;

import static android.Manifest.permission.READ_MEDIA_IMAGES;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chat.familyimagechat.R;
import com.chat.familyimagechat.databinding.ActivityMainBinding;
import com.chat.familyimagechat.feature.presentation.models.ImageChatUI;
import com.chat.familyimagechat.utils.Utils;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_READ_MEDIA_IMAGES = 0X01;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 0X02;
    private final ChatItemPaddingDecorator decorator = new ChatItemPaddingDecorator(Utils.dpToPx(8));
    private ImageChatAdaptor imageChatAdaptor;
    private ActivityMainBinding binding;
    private final ActivityResultLauncher<Intent> mStartForResultSettings = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (hasPermission()) {
                binding.allowPermission.setVisibility(View.GONE);
                binding.imageChatList.setVisibility(View.VISIBLE);
                binding.fab.setVisibility(View.VISIBLE);
            } else {
                binding.allowPermission.setVisibility(View.VISIBLE);
                binding.imageChatList.setVisibility(View.GONE);
                binding.fab.setVisibility(View.GONE);
            }
        }
    });
    private boolean requestFromButton = false;
    private FamilyImageChatViewModel viewModel;
    private LinearLayoutManager layoutManager;
    private final ActivityResultLauncher<Intent> mStartForResultImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();

                try {

                    new Handler().postDelayed(() -> {
                        viewModel.upsertChat(new ImageChatUI(viewModel.chats.size(), imageUri.toString(), Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()), true));
                        submitData();
                        new Handler().postDelayed(MainActivity.this::scrollToBottom, 200);
                    }, 200);

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, R.string.failed_to_load_image, Toast.LENGTH_SHORT).show();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        layoutManager = new LinearLayoutManager(this);
        viewModel = new ViewModelProvider(this).get(FamilyImageChatViewModel.class);
        imageChatAdaptor = new ImageChatAdaptor();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupSupportActionBar();
        setupRecyclerView();
        setupClickListeners();
        if (!hasPermission()) {
            requestPermissions();
        }
        getChatsFromDB();
        onAutoResponseReceived();

    }


    private void onAutoResponseReceived() {
        viewModel.setOnAutoResponseReceived(() -> {
            submitData();
            new Handler().postDelayed(this::scrollToBottom, 200);
        });
    }

    private void getChatsFromDB() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.allowPermission.setVisibility(View.GONE);
        binding.imageChatList.setVisibility(View.GONE);
        binding.fab.setVisibility(View.GONE);
        viewModel.setOnChatsReceived(() -> runOnUiThread(() -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.imageChatList.setVisibility(View.VISIBLE);
            binding.fab.setVisibility(View.VISIBLE);
            submitData();
            new Handler().postDelayed(this::scrollToBottom, 200);
        }));
    }


    private void submitData() {
        imageChatAdaptor.submitList(viewModel.chats);
        binding.imageChatList.removeItemDecoration(decorator);
        binding.imageChatList.addItemDecoration(decorator);
    }

    private void setupClickListeners() {
        binding.fab.setOnClickListener(v -> {
            checkPermissionAndLaunchForImage();
        });
        binding.allowPermission.setOnClickListener(v -> {
            requestFromButton = true;
            checkPermissionAndLaunchForImage();
        });
    }

    private void checkPermissionAndLaunchForImage() {
        if (hasPermission()) {
            launchForImage();
        } else {
            requestPermissions();
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return checkSelfPermission(READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
        }
        return checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{READ_MEDIA_IMAGES}, REQUEST_CODE_READ_MEDIA_IMAGES);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_MEDIA_IMAGES);
        }
    }

    private void setupRecyclerView() {
        binding.imageChatList.setAdapter(imageChatAdaptor);
        binding.imageChatList.setLayoutManager(layoutManager);
        binding.imageChatList.setItemAnimator(new DefaultItemAnimator());

    }

    private void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(viewModel.chats.size() - 1, 0);
    }

    private void setupSupportActionBar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.app_name));
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
    }

    private void launchForImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mStartForResultImage.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_READ_MEDIA_IMAGES || requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                launchForImage();
                binding.allowPermission.setVisibility(View.GONE);
                binding.imageChatList.setVisibility(View.VISIBLE);
                binding.fab.setVisibility(View.VISIBLE);
            } else {
                binding.allowPermission.setVisibility(View.VISIBLE);
                binding.imageChatList.setVisibility(View.GONE);
                binding.fab.setVisibility(View.GONE);
                if (requestFromButton) {
                    settingsAlertDialog();
                }
            }
        }
    }

    private void settingsAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.permission_required);
        builder.setMessage(R.string.navigate_to_setting_for_permission);
        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            mStartForResultSettings.launch(intent);
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}