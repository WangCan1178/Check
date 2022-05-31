package com.example.check.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.check.Adapter.TaskCApater;
import com.example.check.Entity.Task;
import com.example.check.R;
//import com.example.check.addtask.AddTaskDXXActivity;
import com.example.check.addtask.AddTaskJKMActivity;
import com.example.check.addtask.AddTaskXCMActivity;
import com.example.check.addtask.AddTaskZDYActivity;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link cTaskFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link cTaskFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class cTaskFragment extends Fragment {
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

    public cTaskFragment() {
        // Required empty public constructor
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment cTaskFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static cTaskFragment newInstance(String param1, String param2) {
//        cTaskFragment fragment = new cTaskFragment();
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

    private ImageView but_cAddTask;

    private String baseURL = "http://10.0.2.2:9000/manage";
    private SharedPreferences sp;
    private List<Task> tasks;
    private TaskCApater adapter;
    private ListView listView;
    private Handler handler;
    private  int id;
@Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable   Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_c_task, container, false);

        Bundle bundle = getArguments();
        id = bundle.getInt("groupid");

        sp = getActivity().getSharedPreferences("onClick", MODE_PRIVATE);
        listView = (ListView)view.findViewById(R.id.taskclist) ;
        handler = new Handler();

        StringBuilder stringBuilder = new StringBuilder(baseURL+"/getTask");
        stringBuilder.append("?");
        stringBuilder.append("id").append("=").append(id);
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
                JSONArray jsonArray = null;
                tasks = new ArrayList<>();
                if (result.equals("")){
                    Looper.prepare();
                    DynamicToast toast = new DynamicToast();
                    Toast toast1 = toast.makeError(getActivity(),"出错啦");
                    toast1.setGravity(Gravity.TOP,0,50);
                    toast1.show();
                    Looper.loop();
                    return;
                }else {
                    try {
                        jsonArray = new JSONArray(result);
                        for(int i=0;i<jsonArray.length();i++){
                            Task task = new Task();
                            task.setTaskid(jsonArray.getJSONObject(i).getInt("taskid"));
                            task.setTitle(jsonArray.getJSONObject(i).getString("title"));
                            tasks.add(task);
                        }
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                adapter = new TaskCApater(getActivity(),R.layout.task_item,tasks);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Intent intent =new Intent(getActivity(), com.example.check.TaskActivity.class);
                                        Bundle bundle=new Bundle();
                                        bundle.putInt("taskid",tasks.get(i).getTaskid());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                            }
                        };
                        new Thread(){
                            public void run(){
                                handler.post(runnable);
                            }
                        }.start();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        but_cAddTask = (ImageView) view.findViewById(R.id.but_cAddTask);
        but_cAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(but_cAddTask);
            }
        });
        return view;
    }
    private void showPopupMenu(final View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(getActivity(),view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.ctask, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(getActivity(),item.getTitle(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), item.getItemId(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(),item.getTitle().toString()),Toast.LENGTH_SHORT).show();
//                先进入createActivity在进来cTaskFragment,相应按钮进入activity，再进入Fragment
                switch (item.getItemId()){
                    case R.id.jkm:
                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        Intent intentJKM = new Intent(getActivity(), AddTaskJKMActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("groupid",id);
                        intentJKM.putExtras(bundle);
                        startActivity(intentJKM);
                        return true;
//                        break;
                    case R.id.xcm:
                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        Intent intentXCM = new Intent(getActivity(), AddTaskXCMActivity.class);
                        Bundle bundle2=new Bundle();
                        bundle2.putInt("groupid",id);
                        intentXCM.putExtras(bundle2);
                        startActivity(intentXCM);
                        return true;
//                        break;
//                    case R.id.dxx:
//                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
//                        Intent intentDXX = new Intent(getActivity(), AddTaskDXXActivity.class);
//                        startActivity(intentDXX);
//                        return true;
//                        break;
                    case R.id.zdy:
                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        Intent intentZDY = new Intent(getActivity(), AddTaskZDYActivity.class);
                        Bundle bundle3=new Bundle();
                        bundle3.putInt("groupid",id);
                        intentZDY.putExtras(bundle3);
                        startActivity(intentZDY);
                        return true;
                    default:
                        return false;
//                        break;
                }
//                Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
//                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getActivity(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });

        popupMenu.show();

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
