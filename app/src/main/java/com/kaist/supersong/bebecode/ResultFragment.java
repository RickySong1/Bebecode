package com.kaist.supersong.bebecode;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import DataStructure.BabyListData;
import DataStructure.BabyResultCard;
import mymanager.MySocketManager;
import questions.Month27_29;
import questions.MonthQuestions;

import static com.kaist.supersong.bebecode.CheckFragment.CHILDID;
import static com.kaist.supersong.bebecode.CheckFragment.CHILD_NAME;
import static com.kaist.supersong.bebecode.CheckFragment._items;
import static com.kaist.supersong.bebecode.CheckFragment.fileM;
import static com.kaist.supersong.bebecode.MainActivity.USERTYPE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance(String param1, String param2) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=null;
        rootView = inflater.inflate(R.layout.result_show_form, container, false);

        RecyclerView recyclerView=(RecyclerView)rootView.findViewById(R.id.devel_input_recycleview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(rootView.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<BabyResultCard> items=new ArrayList<>();

        Boolean isShort = false;

            String month = CheckFragment.CHILD_MONTH;
            MonthQuestions myMonthQuestion = fileM.getMonthQuestions(month);
            isShort = myMonthQuestion.isShortMonth();
            String _id = CheckFragment.CHILDID;
            MySocketManager socketM = new MySocketManager(USERTYPE);

            TextView text_temp = (TextView) rootView.findViewById(R.id.develinput_name);
            text_temp.setText(CHILD_NAME);
            text_temp = (TextView) rootView.findViewById(R.id.develinput_status);
            text_temp.setText(CheckFragment.CHILD_BIRTH);

            int [] checkResult = new int[6];

            for(int i=0 ; i< _items.size() ; i++) {

                if (_items.get(i).getClicked_radio() >= 0 && _items.get(i).getClicked_radio() < 4 && _items.get(i).getSpouse_cliccked() >= 0 && _items.get(i).getSpouse_cliccked() < 4) {
                    if(checkResult[_items.get(i).getQuestion_type()] >= 0)
                        checkResult[_items.get(i).getQuestion_type()] += Math.min(_items.get(i).getClicked_radio() , _items.get(i).getSpouse_cliccked());
                    else
                        continue;
                }
                else{
                    checkResult[_items.get(i).getQuestion_type()] = -1000; // no check
                }

                if(_items.get(i).isConflict()){  // conflict -> minus value
                    checkResult[_items.get(i).getQuestion_type()] = -100; // conflict
                }
            }

            items.add(new BabyResultCard(month+"개월", checkResult, myMonthQuestion ) );

        // need month , result , month object
        //items.add(new BabyResultCard("결과Sample A",new int[]{22,15,14,16,18,15} , new Month27_29()) );
        //items.add(new BabyResultCard("결과Sample B",new int[]{12,12,11,-5,1,-6} , new Month24_26()) );

        recyclerView.setAdapter(new RecyclerAdapterResult(rootView.getContext(),  items,   R.layout.result_show_form , isShort));
        return rootView;
    }



}
