package com.qlckh.purifier.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.qlckh.purifier.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.provider.VoicemailContract.Voicemails.SOURCE_DATA;

/**
 * @author Andy
 * @date 2018/5/25 15:28
 * Desc: 选择发送人
 */
public class SelectDialog extends DialogFragment {

    @BindView(R.id.listview)
    ListView listview;
    Unbinder unbinder;
    @BindView(R.id.bt_sure)
    Button btSure;
    private Activity mContext;
    private List<String> mDatas;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (Activity) context;

    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        Window window = getDialog().getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        WindowManager windowManager = mContext.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        attributes.width = (int) (display.getWidth() * 0.5);
        attributes.height = (int) (display.getHeight()*0.8);
        attributes.gravity = Gravity.END;
        assert window != null;
        window.setAttributes(attributes);
        window.setWindowAnimations(R.style.select_dialog_animation);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        assert arguments != null;
        mDatas = arguments.getStringArrayList(SOURCE_DATA);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Window window = getDialog().getWindow();
        assert window != null;
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawableResource(android.R.color.white);
        View view = inflater.inflate(R.layout.frg_select_dialog, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,
                android.R.id.text1, mDatas);
        listview.setAdapter(adapter);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_sure)
    public void onViewClicked() {

        dismiss();
    }

    public static SelectDialog getInstance(List<String> datas) {
        SelectDialog selectDialog = new SelectDialog();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(SOURCE_DATA, (ArrayList<String>) datas);
        selectDialog.setArguments(bundle);
        return selectDialog;
    }
}
