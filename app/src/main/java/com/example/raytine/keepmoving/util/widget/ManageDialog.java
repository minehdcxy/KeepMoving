package com.example.raytine.keepmoving.util.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.raytine.keepmoving.R;

public class ManageDialog extends Dialog{

    private DialogOnClickListener listener;

    public ManageDialog(Context context, DialogOnClickListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_manage);

        TextView edit = (TextView) findViewById(R.id.dialog_edit_tv);
        TextView delete = (TextView) findViewById(R.id.dialog_delete_tv);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickPosition(0);
                dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickPosition(1);
                dismiss();
            }
        });
    }

    public interface DialogOnClickListener {
        void clickPosition(int position);
    }

}
