//package com.hancher.gamelife.bak.activity;
//
//import android.Manifest;
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.LinkProperties;
//import android.net.Network;
//import android.net.NetworkCapabilities;
//import android.net.NetworkRequest;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.hancher.common.base.BaseActivity;
//import com.hancher.common.utils.android.LogUtil;
//import com.hancher.common.utils.android.NetConnectUtil;
//import com.hancher.gamelife.MainApplication;
//import com.hancher.gamelife.R;
//import com.hancher.gamelife.bak.contract.RepairDbContract;
//import com.hancher.gamelife.databinding.RepairDbActivityBinding;
//import com.hancher.gamelife.bak.presenter.RepairDbPresenter;
//
//import java.util.HashMap;
//
//public class RepairDbActivity extends BaseActivity<RepairDbActivityBinding, RepairDbPresenter>
//        implements RepairDbContract.View, View.OnClickListener {
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding.btnRepairDate.setOnClickListener(this);
//        binding.btnRepairAccount.setOnClickListener(this);
//        binding.btnImportData.setOnClickListener(this);
//        binding.btnLocalData.setOnClickListener(this);
//        binding.btnTestNet.setOnClickListener(this);
//        binding.btnNetInfo.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btn_repair_date:
//                presenter.repairDb();
//                break;
//            case R.id.btn_repair_account:
//                presenter.repairAccountDb();
//                break;
//            case R.id.btn_import_data:
//                LogUtil.d();
//                presenter.importData();
//                break;
//            case R.id.btn_local_data:
//                LogUtil.d();
//                presenter.repairNoteLocation();
//                break;
//            case R.id.btn_test_net:
//                presenter.testNet();
//                break;
//            case R.id.btn_net_info:
//                StringBuffer s = new StringBuffer();
//                s.append(NetConnectUtil.getConnectedType());
//                s.append("\n");
//                s.append(NetConnectUtil.isMobileConnected());
//                s.append("\n");
//                s.append(NetConnectUtil.isWifiConnected());
//                s.append("\n");
//                binding.txtLog.append(s);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    ConnectivityManager connectivityManager = (ConnectivityManager) MainApplication.getInstance()
//                            .getSystemService(Context.CONNECTIVITY_SERVICE);
//                    if (connectivityManager != null) {
//                        connectivityManager.registerNetworkCallback(
//                                new NetworkRequest.Builder().build(),
//                                new ConnectivityManager.NetworkCallback() {
//                                    @Override
//                                    public void onAvailable(@NonNull Network network) {
//                                        super.onAvailable(network);
//                                        LogUtil.i("网络连接了");
//                                    }
//
//                                    @Override
//                                    public void onLosing(@NonNull Network network, int maxMsToLive) {
//                                        super.onLosing(network, maxMsToLive);
//                                        LogUtil.i("网络正在断开");
//                                    }
//
//                                    @Override
//                                    public void onLost(@NonNull Network network) {
//                                        super.onLost(network);
//                                        LogUtil.i("网络已断开");
//                                    }
//
//                                    @Override
//                                    public void onUnavailable() {
//                                        super.onUnavailable();
//                                        LogUtil.i("网络不可获得");
//                                    }
//
//                                    @Override
//                                    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
//                                        super.onCapabilitiesChanged(network, networkCapabilities);
//                                        //网络变化时，这个方法会回调多次
//                                        LogUtil.i("网络变化");
//                                        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
//                                            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
//                                                LogUtil.d("wifi网络已连接");
////                    handleOnNetworkChange(NETWORK_STATE_AVAILABLE_WIFI);
//
//                                            } else {
//                                                LogUtil.d("移动网络已连接");
////                    handleOnNetworkChange(NETWORK_STATE_AVAILABLE_MOBILE);
//                                            }
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
//                                        super.onLinkPropertiesChanged(network, linkProperties);
//                                        LogUtil.i("");
//                                    }
//
//                                    @Override
//                                    public void onBlockedStatusChanged(@NonNull Network network, boolean blocked) {
//                                        super.onBlockedStatusChanged(network, blocked);
//                                        LogUtil.i("");
//                                    }
//                                });
//                    }
//                } else {
//                    LogUtil.i("");
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void updateNoteResult(Integer createtime, Integer updatetime, Integer deleteflag) {
//        binding.txtLog.append("修复无创建时间条数："+createtime+"\n修复更新时间条数："+updatetime+"\n修复删除状态条数："+updatetime);
//    }
//
//    @Override
//    public void updateAccountResult(HashMap<String, Integer> o) {
//        binding.txtLog.append("修复更新时间条数：" + o.get("updatetime") + "\n修复删除状态条数：" + o.get("deleteflag"));
//    }
//
//    @Override
//    public void updateLogView(String o) {
//        binding.txtLog.append("\n" + o);
//    }
//
//    @Override
//    public String[] getPermission() {
//        return new String[]{Manifest.permission.ACCESS_NETWORK_STATE};
//    }
//}