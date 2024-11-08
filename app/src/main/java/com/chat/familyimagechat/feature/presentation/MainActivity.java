package com.chat.familyimagechat.feature.presentation;

import static android.Manifest.permission.READ_MEDIA_IMAGES;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_READ_MEDIA_IMAGES = 0X01;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 0X02;
    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    private boolean requestFromButton = false;
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()
                    == RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    //imageView.setImageBitmap(bitmap);
                    Log.i(TAG, "onActivityResult: " + imageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FamilyImageChatViewModel viewModel = new ViewModelProvider(this).get(FamilyImageChatViewModel.class);
//        viewModel.upsertChat(new ImageChatUI(
//                1,
//                "https://picsum.photos/200/300",
//                Instant
//                        .ofEpochMilli(System.currentTimeMillis())
//                        .atZone(ZoneId.systemDefault()),
//                true,
//                MessageDelivery.DELIVERED
//        ));
        // viewModel.getAllChats();
        setupSupportActionBar();
        setupRecyclerView();
        setupClickListeners();

    }

    @Override
    protected void onStart() {
        super.onStart();
        requestFromButton = false;
        checkPermissionAndLaunchForImage();
    }


    private void setupClickListeners() {
        binding.fab.setOnClickListener(v -> {
            checkPermissionAndLaunchForImage();
        });
        binding.allowPermission.setOnClickListener( v -> {
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
        List<ImageChatUI> chats = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            chats.add(
//                    new ImageChatUI(i,
//                            "https://picsum.photos/200/300",
//                            Instant
//                                    .ofEpochMilli(System.currentTimeMillis())
//                                    .atZone(ZoneId.systemDefault()),
//                            i % 2 == 0,
//                            MessageDelivery.DELIVERED
//                    )
//            );
//        }
        binding.imageChatList.setAdapter(new ImageChatAdaptor(chats));
        binding.imageChatList.setLayoutManager(new LinearLayoutManager(this));
        binding.imageChatList.setItemAnimator(new DefaultItemAnimator());
        binding.imageChatList.addItemDecoration(new ChatItemPaddingDecorator(Utils.dpToPx(8), chats));
    }

    private void setupSupportActionBar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(
                getString(R.string.app_name)
        );
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
    }

    private void launchForImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mStartForResult.launch(intent);
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
                if (requestFromButton){
                    settingsAlertDialog();
                }
            }
        }
    }

    private void settingsAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission Required");
        builder.setMessage("Navigate to Setting for permission");
        builder.setPositiveButton("OK", (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}