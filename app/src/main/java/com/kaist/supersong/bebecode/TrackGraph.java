package com.kaist.supersong.bebecode;

import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import DataStructure.BabyListData;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.*;
import mymanager.MyFileManager;
import mymanager.MySocketManager;
import questions.MonthQuestions;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrackGraph.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrackGraph#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackGraph extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressBar progressbar;

    TableRow supportLine;
    ArrayList<String> socket_track_data;
    LineChartView chart;
    List<Line> lines;
    LineChartData data;

    Line line1; Line line2; Line line3; Line line4; Line line5; Line line6; Line line_dot;
    Line help1; Line help2;

    List<PointValue> values1; List<PointValue> values2; List<PointValue> values3; List<PointValue> values4; List<PointValue> values5; List<PointValue> values6; List<PointValue> values_dot;

    private OnFragmentInteractionListener mListener;

    Switch [] switch_g;

    View jajoview;
    TextView jajotext;

    public TrackGraph() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrackGraph.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackGraph newInstance(String param1, String param2) {
        TrackGraph fragment = new TrackGraph();
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
        rootView =inflater.inflate(R.layout.fragment_track_graph, container, false);

        supportLine = (TableRow) rootView.findViewById(R.id.tablerow_helpline);

        switch_g = new Switch[6];

        switch_g[0] = (Switch) rootView.findViewById(R.id.switch_big);
        switch_g[1] = (Switch) rootView.findViewById(R.id.switch_small);
        switch_g[2] = (Switch) rootView.findViewById(R.id.switch_recog);
        switch_g[3] = (Switch) rootView.findViewById(R.id.switch_lan);
        switch_g[4] = (Switch) rootView.findViewById(R.id.switch_social);
        switch_g[5] = (Switch) rootView.findViewById(R.id.switch_self);

        jajoview = (View) rootView.findViewById(R.id.viewjajo);
        jajotext = (TextView) rootView.findViewById(R.id.textjajo);

        lines = new ArrayList<Line>();
        socket_track_data = new ArrayList<String>();
        progressbar = (ProgressBar) rootView.findViewById(R.id.graph_loading_progress_bar);
        chart = (LineChartView) rootView.findViewById(R.id.chart);

        new ProgressTask().execute();
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class ProgressTask extends AsyncTask<String, Integer, String> {

        String month,_id, birth_s;
        MyFileManager fileM;
        ArrayList<BabyListData> list_itemArrayList = new ArrayList<BabyListData>();
        MySocketManager socketM;
        MonthQuestions myMonthQuestion;
        int average_delay_score = 0;
        Boolean isShort = false;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            fileM = new MyFileManager();
            fileM.getChildList(list_itemArrayList);
            birth_s = fileM.getMonthString(list_itemArrayList.get(0).getMonth());
            _id = list_itemArrayList.get(0).getId();

            socketM = new MySocketManager(fileM.getUserType());
        }

        @Override
        protected String doInBackground(String... params) {
            String get_message = socketM.getTcpIpResultTrack(_id , MySocketManager.GET_TRACK , birth_s);

            for(int i=0; i< get_message.split("@@").length-1 ; i++){
                socket_track_data.add(get_message.split(("@@"))[i+1]);
            }


            ArrayList<BabyListData> list_itemArrayList = new ArrayList<BabyListData>();

            String month;
                fileM = new MyFileManager();
                fileM.getChildList(list_itemArrayList);
                list_itemArrayList.add(new BabyListData(R.drawable.father," ","아동 추가","0000","10")); // the last item is automatically added
                if(list_itemArrayList.size() > 1 ) {
                    month = list_itemArrayList.get(0).getMonth();
                    myMonthQuestion = fileM.getMonthQuestions(month);
                    _id = list_itemArrayList.get(0).getId();
                    isShort = myMonthQuestion.isShortMonth();
                }

                int total_count = 0;

                for(int i=0 ; i< myMonthQuestion.getAnswerList().size() ; i++){
                    average_delay_score += myMonthQuestion.getAnswerList().get(i)[0];
                    if( myMonthQuestion.getAnswerList().get(i)[0] !=0 )
                        total_count++;
                }
                average_delay_score = average_delay_score / total_count;

            return null;
        }

        private class ValueTouchListener implements LineChartOnValueSelectListener {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                // Graph click event
                Log.e("zzz",Integer.toString(lineIndex)+" " + Integer.toString(pointIndex)+" " + value.toString());
            }

            @Override
            public void onValueDeselected() { }
        }

        public void checkOneLine(){

            if( lines.size() == 1){
                supportLine.setVisibility(TableRow.VISIBLE);
                List<PointValue> help_value1 = new ArrayList<PointValue>();
                List<PointValue> help_value2 = new ArrayList<PointValue>();

                if(lines.get(0) == line1){
                    for(int i=0 ; i< values1.size() ; i++){
                        help_value1.add(new PointValue(i,myMonthQuestion.getAnswerList().get(0)[0]));
                        help_value2.add(new PointValue(i,myMonthQuestion.getAnswerList().get(0)[1]));
                    }
                }else if( lines.get(0) == line2){
                    for(int i=0 ; i< values2.size() ; i++){
                        help_value1.add(new PointValue(i,myMonthQuestion.getAnswerList().get(0)[0]));
                        help_value2.add(new PointValue(i,myMonthQuestion.getAnswerList().get(0)[1]));
                    }
                }else if( lines.get(0) == line3){
                    for(int i=0 ; i< values3.size() ; i++){
                        help_value1.add(new PointValue(i,myMonthQuestion.getAnswerList().get(0)[0]));
                        help_value2.add(new PointValue(i,myMonthQuestion.getAnswerList().get(0)[1]));
                    }
                }else if( lines.get(0) == line4){
                    for(int i=0 ; i< values4.size() ; i++){
                        help_value1.add(new PointValue(i,myMonthQuestion.getAnswerList().get(0)[0]));
                        help_value2.add(new PointValue(i,myMonthQuestion.getAnswerList().get(0)[1]));
                    }
                }else if( lines.get(0) == line5){
                    for(int i=0 ; i< values5.size() ; i++){
                        help_value1.add(new PointValue(i,myMonthQuestion.getAnswerList().get(0)[0]));
                        help_value2.add(new PointValue(i,myMonthQuestion.getAnswerList().get(0)[1]));
                    }
                }else if( lines.get(0) == line6){
                    for(int i=0 ; i< values6.size() ; i++){
                        help_value1.add(new PointValue(i,myMonthQuestion.getAnswerList().get(0)[0]));
                        help_value2.add(new PointValue(i,myMonthQuestion.getAnswerList().get(0)[1]));
                    }
                }
                help1 = new Line(help_value1).setColor(Color.parseColor("#FF0000")).setHasLabels(true).setHasLines(true);
                help2 = new Line(help_value2).setColor(Color.parseColor("#AA0000")).setHasLabels(true).setHasLines(true);
                lines.add(help1);
                lines.add(help2);
            }else{
                int count = 0;
                while(lines.size() > count){
                    if( lines.get(count) == help1 || lines.get(count) == help2){
                        lines.remove(count);
                        count = 0;
                    }
                    count++;
                }
                supportLine.setVisibility(TableRow.GONE);
            }
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressbar.setVisibility(ProgressBar.GONE);

            chart.setInteractive(false);
            chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);

            Axis axisX = new Axis().setTextColor(Color.BLACK);
            Axis axisY = new Axis().setTextColor(Color.BLACK);
            List<AxisValue> axisValues = new ArrayList<AxisValue>();

            axisY.setName("발달점수");

            values1 = new ArrayList<PointValue>();
            values2 = new ArrayList<PointValue>();
            values3 = new ArrayList<PointValue>();
            values4 = new ArrayList<PointValue>();
            values5 = new ArrayList<PointValue>();
            values6 = new ArrayList<PointValue>();
            values_dot = new ArrayList<PointValue>();
            //In most cased you can call data model methods in builder-pattern-like manner.

            axisValues = new ArrayList<AxisValue>();
            for(int i=0 ;  i< socket_track_data.size() && socket_track_data.get(i).split(" ").length > 5; i++){
                axisValues.add(new AxisValue(i).setLabel(socket_track_data.get(i).split(" ")[0]));
                if(socket_track_data.get(i).split(" ")[1].compareTo("fail")!=0)
                    values1.add( new PointValue(i,Integer.parseInt(socket_track_data.get(i).split(" ")[1])));
                if(socket_track_data.get(i).split(" ")[2].compareTo("fail")!=0)
                    values2.add( new PointValue(i,Integer.parseInt(socket_track_data.get(i).split(" ")[2])));
                if(socket_track_data.get(i).split(" ")[3].compareTo("fail")!=0)
                    values3.add( new PointValue(i,Integer.parseInt(socket_track_data.get(i).split(" ")[3])));
                if(socket_track_data.get(i).split(" ")[4].compareTo("fail")!=0)
                    values4.add( new PointValue(i,Integer.parseInt(socket_track_data.get(i).split(" ")[4])));
                if(socket_track_data.get(i).split(" ")[5].compareTo("fail")!=0)
                    values5.add( new PointValue(i,Integer.parseInt(socket_track_data.get(i).split(" ")[5])));
                if(socket_track_data.get(i).split(" ")[6].compareTo("fail")!=0) {
                    values6.add(new PointValue(i, Integer.parseInt(socket_track_data.get(i).split(" ")[6])));
                }
                values_dot.add(new PointValue(i,average_delay_score));
            }
            //values1.add( new PointValue(5,5));
            line1 = new Line(values1).setColor(Color.parseColor("#a49600")).setHasLabels(true).setHasLines(true);
            line2 = new Line(values2).setColor(Color.RED).setHasLabels(true).setHasLines(true);
            line3 = new Line(values3).setColor(Color.parseColor("#ffcc33")).setHasLabels(true).setHasLines(true);
            line4 = new Line(values4).setColor(Color.BLACK).setHasLabels(true).setHasLabels(true).setHasLines(true);
            line5 = new Line(values5).setColor(Color.parseColor("#8c0896")).setHasLabels(true).setHasLines(true);
            line6 = new Line(values6).setColor(Color.GRAY).setHasLabels(true).setHasLabels(true).setHasLines(true);
            line_dot = new Line(values_dot).setColor(Color.parseColor("#5c98ff")).setHasLabels(false).setHasLines(true).setHasLabelsOnlyForSelected(true);

            if(!isShort) {
                lines.add(line6);
            }else{
                jajoview.setVisibility(View.INVISIBLE);
                jajotext.setVisibility(TextView.INVISIBLE);
                switch_g[5].setVisibility(Switch.INVISIBLE);
            }
            lines.add(line1);
            lines.add(line3);
            lines.add(line2);
            lines.add(line5);
            lines.add(line4);
            //lines.add(line_dot);

            data = new LineChartData();

            data.setLines(lines);
            data.setAxisYLeft(axisY);
            data.setAxisXBottom(new Axis(axisValues).setTextColor(Color.BLACK).setTextSize(10));

            chart.setLineChartData(data);
            chart.setInteractive(true);
            chart.setZoomType(ZoomType.HORIZONTAL);
            //chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);

            final Viewport v = new Viewport(chart.getMaximumViewport());
            v.bottom = 0;
            v.top = 24;//a little more than 200 to keep labels visible
            chart.setMaximumViewport(v);
            chart.setCurrentViewport(v);
//Optional step: disable viewport recalculations, thanks to this animations will not change viewport automatically.
            chart.setViewportCalculationEnabled(false);
            chart.setOnValueTouchListener(new ValueTouchListener());

            for(int i=0 ; i< 6 ; i++)
                switch_g[i].setChecked(true);

            switch_g[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        lines.add(line1);
                    } else {
                        for(int i=0 ; i< lines.size() ; i++){
                            if( line1 == lines.get(i)){
                                lines.remove(i);
                            }
                        }
                    }
                    checkOneLine();
                    data.setLines(lines);
                    chart.setLineChartData(data);
                }
            });
            switch_g[1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        lines.add(line2);
                    } else {
                        for(int i=0 ; i< lines.size() ; i++){
                            if( line2 == lines.get(i)){
                                lines.remove(i);
                            }
                        }
                    }
                    checkOneLine();
                    data.setLines(lines);
                    chart.setLineChartData(data);
                }
            });
            switch_g[2].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        lines.add(line3);
                    } else {
                        for(int i=0 ; i< lines.size() ; i++){
                            if( line3 == lines.get(i)){
                                lines.remove(i);
                            }
                        }
                    }
                    checkOneLine();
                    data.setLines(lines);
                    chart.setLineChartData(data);
                }
            });
            switch_g[3].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        lines.add(line4);
                    } else {
                        for(int i=0 ; i< lines.size() ; i++){
                            if( line4 == lines.get(i)){
                                lines.remove(i);
                            }
                        }
                    }
                    checkOneLine();
                    data.setLines(lines);
                    chart.setLineChartData(data);
                }
            });
            switch_g[4].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        lines.add(line5);
                    } else {
                        for(int i=0 ; i< lines.size() ; i++){
                            if( line5 == lines.get(i)){
                                lines.remove(i);
                            }
                        }
                    }
                    checkOneLine();
                    data.setLines(lines);
                    chart.setLineChartData(data);
                }
            });
            switch_g[5].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        lines.add(line6);
                    } else {
                        for(int i=0 ; i< lines.size() ; i++){
                            if( line6 == lines.get(i)){
                                lines.remove(i);
                            }
                        }
                    }
                    checkOneLine();
                    data.setLines(lines);
                    chart.setLineChartData(data);

                }
            });
        }
    }
}
