package com.sxh.olddriver.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxh.olddriver.R;
import com.sxh.olddriver.activity.MainActivity;
import com.sxh.olddriver.db.MyDBHelper;

import java.util.ArrayList;
import java.util.HashMap;

import demo.LoginPage;

/**
 * Created by user on 2016/7/23.
 */
public class MeFragment extends BaseFragment implements MainActivity.Arguments{
    //
    RelativeLayout login;
    private ImageView icon;
    private TextView name;
    ArrayList<HashMap<String, String>> list;
    @Override
    public View initView() {
        ((MainActivity)getActivity()).setSomeAction(this);
        View view = View.inflate(getActivity(), R.layout.frag_me,null);
        login= (RelativeLayout) view.findViewById(R.id.id_me_login);
        icon=(ImageView)view.findViewById(R.id.me_iv_personal_icon);
        name=(TextView)view.findViewById(R.id.name);
        if (!getData().isEmpty()){
            if (!TextUtils.isEmpty(getData().get(0).get("name"))){
                name.setText(getData().get(0).get("name"));
            }
            if (!TextUtils.isEmpty(getData().get(0).get("icon"))){
                icon.setImageURI(Uri.parse(getData().get(0).get("icon")));
            }
        }else {
            name.setText("布说再见");
            icon.setBackgroundResource(R.drawable.ic_add_qiuyou);
        }
        /*Bundle bundle = getArguments();//从activity传过来的Bundle
        if(bundle!=null){
            if (!TextUtils.isEmpty(bundle.getString("name"))){
                name.setText(bundle.getString("name"));
            }
            if (!TextUtils.isEmpty(bundle.getString("icon"))){
                icon.setImageURI(Uri.parse(bundle.getString("icon")));
            }

        }*/
        return view;
    }

    @Override
    public void setData() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),LoginPage.class);
                startActivityForResult(intent,1);
            }
        });
    }
    public ArrayList<HashMap<String, String>> getData() {
        list = new ArrayList<>();
        Cursor c = MyDBHelper.getInstances(getActivity()).query1();
        if (c.moveToFirst()) {

            do {
                HashMap<String, String> map = new HashMap<>();
                // c.getColumnIndex("title") 获取到title这一列在第几列
                // c.getString()通过列数，得到这一列的这一行的内容
                String icon = c.getString(c.getColumnIndex("icon"));
                String name = c.getString(c.getColumnIndex("name"));
                int id = c.getInt(c.getColumnIndex("id"));
                map.put("id", "" + id);
                map.put("icon",icon);
                map.put("name",name);
                list.add(map);
            } while (c.moveToNext());
        }
        return list;
    }



    @Override
    public void someAction(int a) {
         if (getData().get(0).get("name").equals(null)){

            //if (getData().get(0).get("name")!=null){
                name.setText(getData().get(0).get("name"));
            //}else {

            //if (getData().get(0).get("icon")!=null){
                icon.setImageURI(Uri.parse(getData().get(0).get("icon")));
            //}else {

            }
        else {
        name.setText("布说再见");
        icon.setBackgroundResource(R.drawable.ic_add_qiuyou);
        }
    }
}
