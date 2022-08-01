package com.hancher.gamelife.test;

import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.hancher.common.base.mvp01.BaseActivity;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.databinding.ActivityZxingTestBinding;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

/**
 * 测试zxing
 */
public class ZxingTestActivity extends BaseActivity<ActivityZxingTestBinding> {
    // Register the launcher and result handler
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(ZxingTestActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ZxingTestActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            });
    @Override
    protected void initView() {
        binding.button11.setOnClickListener(view -> {
            ScanOptions options = new ScanOptions()
                    .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                    .setPrompt("请对准二维码")
                    .setCameraId(0)
                    .setBeepEnabled(true)
                    .setOrientationLocked(false)
                    .setBarcodeImageEnabled(true);
            barcodeLauncher.launch(options);
        });

        binding.button13.setOnClickListener(view -> {
            try {
                String content = "富强民主，文明和谐；\n自由平等，公正法制；\n爱国敬业，诚信友善。";
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.encodeBitmap("content", BarcodeFormat.QR_CODE, 400, 400);
                binding.imageView15.setImageBitmap(bitmap);
            } catch (WriterException e) {
                LogUtil.e(e.getMessage());
            }
        });
    }
}