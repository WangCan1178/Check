package com.example.check.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.check.AddGroupActivity;
import com.example.check.LoginActivity;
import com.example.check.R;
import com.example.check.register.Contact;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link myFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link myFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class myFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;

    public myFragment() {
        // Required empty public constructor
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment myFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static myFragment newInstance(String param1, String param2) {
//        myFragment fragment = new myFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    private TextView my_name;
    private TextView my_phone;
    private TextView my_pass;
    private TextView my_id;
    private TextView my_identify;
    private TextView contact;
    private Button but_exit;
    private Button but_cancel;
    private String baseURL = "http://192.168.43.116:9000";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my, container, false);
        my_name = (TextView) view.findViewById(R.id.my_name);
        my_id = (TextView) view.findViewById(R.id.my_id);
        my_phone = (TextView) view.findViewById(R.id.my_phone);
        my_pass = (TextView) view.findViewById(R.id.my_pass);
        my_identify = (TextView) view.findViewById(R.id.my_identify);
        but_exit = (Button) view.findViewById(R.id.but_exit2);
        contact = (TextView) view.findViewById(R.id.contact);

        but_cancel = (Button) view.findViewById(R.id.but_cancel2);
        SharedPreferences sp = getActivity().getSharedPreferences("onClick",MODE_PRIVATE);
        String name = sp.getString("userName","");
        String id = sp.getString("userId","");
        String password = sp.getString("password","");
        //System.out.println(name+id+password);

        my_name.setText(name);
        my_id.setText(id);
        my_phone.setText(id);
        my_pass.setText(password);

        my_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.check.register.RevisePassActivity.class);
                startActivity(intent);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Contact.class);
                startActivity(intent);
            }
        });
        but_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DynamicToast toast = new DynamicToast();
                Toast toast1 = toast.makeSuccess(getActivity(),"退出成功");
                toast1.setGravity(Gravity.TOP,0,50);
                toast1.show();
                SharedPreferences sp = getActivity().getSharedPreferences("onClick",MODE_PRIVATE);
                sp.edit().clear().commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        but_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

        return view;
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认注销?");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getActivity(), "dialog", Toast.LENGTH_SHORT).show();
                final SharedPreferences sp = getActivity().getSharedPreferences("onClick",MODE_PRIVATE);
                StringBuilder stringBuilder = new StringBuilder(baseURL+"/delUser");
                stringBuilder.append("?");
                stringBuilder.append("id").append("=").append(sp.getString("userId",""));;
                OkHttpClient client = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url(stringBuilder.toString())
                        .get()
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        if (result.equals("success")){
                            sp.edit().clear().commit();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        builder.setCancelable(true);   //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = builder.create();  //创建对话框
        dialog.setCanceledOnTouchOutside(true);      //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();
    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
