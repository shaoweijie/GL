package com.hancher.gamelife.main.human;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.hancher.common.base.mvvm02.BaseActivity;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.BaseAdapterUtil;
import com.hancher.common.utils.android.DialogUtil;
import com.hancher.common.utils.android.ImageUtil;
import com.hancher.common.utils.android.IntentUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.RecyclerViewUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.common.utils.android.UriUtil;
import com.hancher.common.utils.java.DateUtil;
import com.hancher.gamelife.MainApplication;
import com.hancher.gamelife.R;
import com.hancher.gamelife.bak.list.CharacterExtAdapter;
import com.hancher.gamelife.databinding.CharacterEditActivityBinding;
import com.hancher.gamelife.greendao.Character;
import com.hancher.gamelife.greendao.CharacterExt;
import com.hancher.gamelife.greendao.CharacterExtDao;
import com.nlf.calendar.Lunar;
import com.nlf.calendar.Solar;
import com.permissionx.guolindev.PermissionX;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class CharacterEditActivity extends BaseActivity<CharacterEditActivityBinding> {
    private boolean female = true;
    boolean birthdaySolar = true;
    public final static String RECORD_UUID = "RECORD_UUID";
    private MaterialDialog dialog;
    private CharacterExtAdapter adapter = new CharacterExtAdapter(null,this);
    CharactersViewModel viewModel;

    @Override
    protected void initView() {

        ToolBarUtil.initToolbar(binding.superToolbar,this,true);
        RecyclerViewUtil.initLine(this,binding.recyclerview,adapter,null,false);

        String recordUuid = getIntent().getStringExtra(RECORD_UUID);
        if (!TextUtils.isEmpty(recordUuid)) {
            LogUtil.i("查询对应人物 uuid:", recordUuid);
            viewModel.queryCharacter(recordUuid);
        } else {
            Character character = new Character();
            viewModel.getCharacter().setValue(character);
        }
    }

    @Override
    protected void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CharactersViewModel.class);

        viewModel.getCharacterExts().observe(this, characterExts -> {
            LogUtil.i("人物ext发生变化");
            BaseAdapterUtil.update(adapter,characterExts);
        });
        viewModel.getCharacter().observe(this, character -> {
            if (TextUtils.isEmpty(character.getName())) {
                return;
            }
            LogUtil.i("人物发生变化");
            binding.headerName.setText(character.getName());

            female = character.getFemale() == null || character.getFemale();
            binding.female.setImageResource(female ? R.drawable.famale_selected : R.drawable.female_unselect);
            binding.man.setImageResource(female ? R.drawable.man_unselect : R.drawable.man_selected);

            birthdaySolar = character.getBirthdaySolar() == null || character.getBirthdaySolar();
            binding.txtSolar.setTextColor(birthdaySolar ? getResources().getColor(R.color.material_red) : getResources().getColor(R.color.black));
            binding.txtLunar.setTextColor(birthdaySolar ? getResources().getColor(R.color.black) : getResources().getColor(R.color.material_red));
            binding.txtSolar.setText(character.getBirthday());

            binding.txtCompany.setText(character.getCompany());
            binding.txtOriginze.setText(character.getOrganize());
            binding.txtSchool.setText(character.getSchool());

            viewModel.queryHead(character.getHeadPicUuid());
            viewModel.queryExt(character.getUuid());
        });

        viewModel.getHead().observe(this, image -> {
            binding.headerHead.setImageBitmap(ImageUtil.string2Bitmap(image.getContent()));
        });

    }

    @Override
    protected void initListener() {
        binding.txtSolar.setOnClickListener(v -> showBirthday());
        binding.txtLunar.setOnClickListener(v -> showBirthday());
        binding.txtSolar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtil.d("s=" + s);
                Date date = DateUtil.changeDate(s.toString(), DateUtil.Type.STRING_YMD, DateUtil.Type.DATE);
                if (date == null) {
                    return;
                }
                Solar solar = Solar.fromDate(date);
                Lunar lunar = solar.getLunar();
                binding.txtLunar.setText(lunar + " " + lunar.getYearShengXiao());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //添加悬浮按钮
        binding.btnFloat.setOnClickListener(v -> {
            LogUtil.d("");
            List<CharacterExtAdapter.CharacterExtItem> data = getLastExtData();
            CharacterExtAdapter.CharacterExtItem emptyItem = new CharacterExtAdapter.CharacterExtItem();
            data.add(emptyItem);
            LogUtil.d("data->"+data.size());
            BaseAdapterUtil.update(adapter,data);
        });
        //保存悬浮按钮
        binding.btnFloat2.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.headerName.toString())) {
                ToastUtil.show("不允许姓名为空");
                return;
            }
            Character character = viewModel.getCharacter().getValue();
            character.setName(binding.headerName.getText().toString());
            character.setUpdatetime(System.currentTimeMillis());
            character.setBirthday(binding.txtSolar.getText().toString());
            character.setBirthdaySolar(birthdaySolar);
            character.setFemale(female);

            character.setCompany(binding.txtCompany.getText().toString());
            character.setOrganize(binding.txtOriginze.getText().toString());
            character.setSchool(binding.txtSchool.getText().toString());

            List<CharacterExtAdapter.CharacterExtItem> data = getLastExtData();

            Dialog progressDialog = DialogUtil.progress(this).show();
            AsyncUtils.run(emitter -> {
                MainApplication.getInstance().getDaoSession().getCharacterDao().insertOrReplace(character);
                List<CharacterExt> deleteItems = MainApplication.getInstance().getDaoSession().getCharacterExtDao().queryBuilder()
                        .where(CharacterExtDao.Properties.CharacterUuid.eq(character.getUuid())).list();
//                MainApplication.getInstance().getDaoSession().getCharacterExtDao().deleteInTx(deleteItems);
//                for(CharacterExtAdapter.CharacterExtItem item: data){
//                    CharacterExt ce = new CharacterExt();
//                    ce.setKeyType(item.getKey());
//                    ce.setValue(item.getValue());
//                    ce.setCharacterUuid(character.getUuid());
//                    MainApplication.getInstance().getDaoSession().insert(ce);
//                }
                List<CharacterExt> updateExts = new ArrayList<>();
                for(CharacterExtAdapter.CharacterExtItem item: data){
                    CharacterExt saveItem = findExtInDb(deleteItems, item.getUuid());
                    saveItem.setKeyType(item.getKey());
                    saveItem.setValue(item.getValue());
                    saveItem.setCharacterUuid(character.getUuid());
                    updateExts.add(saveItem);
                }
                LogUtil.d("save update exts size="+updateExts.size());
                MainApplication.getInstance().getDaoSession().getCharacterExtDao()
                        .insertOrReplaceInTx(updateExts);
                emitter.onNext(true);
                emitter.onComplete();
            }, (Consumer<Boolean>) aBoolean -> {
                progressDialog.dismiss();
                finish();
            });
        });

        binding.female.setOnClickListener(v -> {
            binding.female.setImageResource(R.drawable.famale_selected);
            binding.man.setImageResource(R.drawable.man_unselect);
            female = true;
        });
        binding.man.setOnClickListener(v -> {
            binding.female.setImageResource(R.drawable.female_unselect);
            binding.man.setImageResource(R.drawable.man_selected);
            female = false;
        });

        binding.headerHead.setOnClickListener(v -> {
            startActivityForResult(IntentUtil.getSelectFileIntent(), 100);
        });
    }

    @Override
    protected void initPermission() {
        PermissionX.init(this)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request((allGranted, grantedList, deniedList) -> {
                    if (!allGranted){
                        ToastUtil.show("以下权限未同意:\n"+deniedList);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    String path = UriUtil.getPathByUri(this, data.getData());
                    LogUtil.d("path=" + path);
                    if (path==null){
                        ToastUtil.show("路径"+data.getData()+"解析为null");
                        return;
                    }
                    selectFace(path);
                }
                break;
        }
    }

    class DialogImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ImageView getImageView() {
            return imageView;
        }

        public DialogImageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }

    class DialogImageAdapter extends RecyclerView.Adapter<DialogImageViewHolder> {

        private Context context;
        private List<Bitmap> faces;

        public DialogImageAdapter(Context context, List<Bitmap> faces) {
            this.context = context;
            this.faces = faces;
        }

        @NonNull
        @NotNull
        @Override
        public DialogImageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            ImageView image = new ImageView(context);
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            DialogImageViewHolder viewHolder = new DialogImageViewHolder(image);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull DialogImageViewHolder holder, int position) {
            LogUtil.d("position=" + position);
            holder.getImageView().setImageBitmap(faces.get(position));
            holder.getImageView().setOnClickListener(v -> {
                if (dialog != null) {
                    dialog.dismiss();
                    viewModel.saveImage(faces.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return faces.size();
        }
    }

    private void selectFace(String path) {
        AsyncUtils.run((ObservableOnSubscribe<List<Bitmap>>) emitter -> {

            Bitmap bitmap = ImageUtil.file2Bitmap(path);
            Bitmap[] faces = ImageUtil.getAllFaceBitmapByClip(bitmap);
            if (faces == null || faces.length == 0) {
                emitter.onNext(new ArrayList<>());
                emitter.onComplete();
                return;
            }
            emitter.onNext(Arrays.asList(faces.clone()));
            emitter.onComplete();
        }, bitmaps -> {
            if (bitmaps.size() == 0) {
                ToastUtil.show("没找到人脸");
                return;
            }
            dialog = DialogUtil.build(CharacterEditActivity.this).title("请选择头像")
                    .adapter(new DialogImageAdapter(CharacterEditActivity.this, bitmaps),
                            new GridLayoutManager(CharacterEditActivity.this, 4))
                    .itemsCallback((dialog, itemView, position, text) -> {
                        LogUtil.d("position=" + position);
                    })
                    .show();
        });
    }

    private CharacterExt findExtInDb(List<CharacterExt> exts, String uuid) {
        for(CharacterExt item:exts){
            if (uuid.equals(item.getUuid())){
                return item;
            }
        }
        return new CharacterExt();
    }

    private List<CharacterExtAdapter.CharacterExtItem> getLastExtData() {
//        List<CharacterExtAdapter.CharacterExtItem> newData = new ArrayList<>();
//        for (int i = 0; i < adapter.getData().size(); i++) {
//            BaseViewHolder vh = (BaseViewHolder)binding.recyclerview.findViewHolderForAdapterPosition(i);
//            CharacterExtAdapter.CharacterExtItem item = new CharacterExtAdapter.CharacterExtItem()
//                    .setKey(((TextView) vh.findView(R.id.ext_key)).getText().toString())
//                    .setValue(((TextView) vh.findView(R.id.ext_value)).getText().toString());
//            newData.add(item);
//        }
//        return newData;
        return adapter.getData();
    }


    private void showBirthday() {
        MaterialDialog.Builder dialogbuild = DialogUtil.build(this);
        CalendarView calendarView = new CalendarView(this);
        calendarView.showYearSelectLayout(1994);
        final MaterialDialog dialog2 = dialogbuild.title("生日：1994")
                .customView(calendarView, true)
                .positiveText("阳历")
                .negativeText("阴历")
                .onPositive((dialog, which) -> {
                    birthdaySolar = true;
                    setBirthdayText(calendarView);
                })
                .onNegative((dialog, which) -> {
                    birthdaySolar = false;
                    setBirthdayText(calendarView);
                })
                .show();
        calendarView.setOnYearChangeListener(year -> {
            if (dialog2 == null) {
                return;
            }
            dialog2.setTitle("生日：" + year);
        });
    }

    private void setBirthdayText(CalendarView calendarView) {
        Calendar calendar = calendarView.getSelectedCalendar();
        String sloar = DateUtil.changeDate(calendar.getTimeInMillis(), DateUtil.Type.LONG_STAMP,
                DateUtil.Type.STRING_YMD);
        binding.txtSolar.setText(sloar);
        binding.txtSolar.setTextColor(birthdaySolar ? getResources().getColor(R.color.material_red) : getResources().getColor(R.color.black));
        binding.txtLunar.setTextColor(birthdaySolar ? getResources().getColor(R.color.black) : getResources().getColor(R.color.material_red));
        LogUtil.d("设置阳历：" + sloar);
    }

}
