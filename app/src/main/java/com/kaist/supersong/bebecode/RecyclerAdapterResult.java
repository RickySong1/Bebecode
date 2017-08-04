package com.kaist.supersong.bebecode;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import DataStructure.BabyResultCard;

/**
 * Created by SuperSong on 2017-03-27.
 */

public class RecyclerAdapterResult extends RecyclerView.Adapter<RecyclerAdapterResult.ViewHolder> {

    Context context;
    List<BabyResultCard> result_items;
    int item_layout;

    final static int numOfResultRows = 6;
    final static int numOfAnswers = 4;

    final static int resultGraphView = 0;
    final static int resultConflict = 1;

    Boolean isShort;

    public RecyclerAdapterResult(Context context, Object items, int item_layout , Boolean _isShort) {
        this.context=context;
        this.item_layout=item_layout;
        this.result_items=  (List<BabyResultCard>) items;
        this.isShort = _isShort;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int _viewType) {
        View v=null;

        switch(_viewType) {
            case resultGraphView:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resultgraph_cardview, null, false);
                break;
            case resultConflict:
               v = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_graph_conflict, null, false);
               break;
        }

        v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(v);
    }

    View.OnClickListener buttonListener2 = new View.OnClickListener() {
        public void onClick(View v) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            Intent intent1 = new Intent(context, MainActivity.class); //인텐트 생성.

            Notification.Builder builder = new Notification.Builder(context.getApplicationContext());
            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

            Bitmap bigPicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.sample2);

            builder.setSmallIcon(R.mipmap.ic_launcher).setTicker("HETT").setWhen(System.currentTimeMillis())
                    .setContentTitle("배우자로부터의 메세지").setContentText("여보 문제 좀 풀어보세요 결과 확인 좀 해보게")
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);

            Notification.BigPictureStyle bigStyle = new Notification.BigPictureStyle(builder);
            bigStyle.setBigContentTitle("from : 배우자");
            bigStyle.setSummaryText("여보 문제좀 풀어봐요 결과 좀 확인해보게");
            bigStyle.bigPicture(bigPicture);
            builder.setStyle(bigStyle);


            notificationManager.notify(1, builder.build()); // Notification send
        }
    };


    View.OnClickListener buttonListener1 = new View.OnClickListener(){
        public void onClick(View v) {

            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_msg_spouse);
            dialog.setTitle("Custom Dialog");

            //TextView tv = (TextView) dialog.findViewById(R.id.text);
            //tv.setText("올린이 : 아빠 (2017/4/12)");

            ImageView iv = (ImageView) dialog.findViewById(R.id.image);
            iv.setImageResource(R.drawable.sample2);

            dialog.show();

            Button dd = (Button) dialog.findViewById(R.id.btn_send2);

            dd.setOnClickListener(buttonListener2);


            NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            Intent intent1 = new Intent(context, MainActivity.class); //인텐트 생성.

            Notification.Builder builder = new Notification.Builder(context.getApplicationContext());
            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

            Bitmap bigPicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.sample2);

            builder.setSmallIcon(R.mipmap.ic_launcher).setTicker("HETT22").setWhen(System.currentTimeMillis())
                    .setContentTitle("Baby development").setContentText("의견차이가 있는 문제가 존재합니다.")
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);

            builder.setOngoing(true);
            notificationManager.notify(2, builder.build()); // Notification send


        }
    };


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //final String item=items.get(position);
        //Drawable drawable=context.getResources().getDrawable(item.getImage());
        //holder.image.setBackground(drawable);

        switch(getItemViewType(position)){
            case resultConflict:
                    holder.msg_to_spouse.setOnClickListener(buttonListener1);
                break;

            case resultGraphView:
                holder.result_graph_title.setText(result_items.get(position).getTitle());
                //Generate Result Graph
                int resultMaximumPoints = 24;
                int[] resultColor = new int[4];

                resultColor[0] = Color.rgb(255, 00, 00);
                resultColor[1] = Color.rgb(170, 00, 00);
                resultColor[2] = Color.rgb(00, 80, 00);
                resultColor[3] = Color.rgb(00, 170, 00);

                TextView[][] resultTextView;
                resultTextView = new TextView[numOfResultRows][resultMaximumPoints];  // 6 parts results , and each part has maximum 24 points

                for (int i = 0; i < numOfResultRows; i++) {

                        int colorSetup = 0;
                        boolean doneMax = false;
                        for (int j = 0; j < resultMaximumPoints; j++) {
                            if (!doneMax && j + 1 >= result_items.get(position).getMyMonth().getAnswerList().get(i)[colorSetup]) {
                                colorSetup++;
                                if (j + 1 >= result_items.get(position).getMyMonth().getAnswerList().get(i)[2])
                                    doneMax = true;
                            }
                            resultTextView[i][j] = new TextView(context);
                            resultTextView[i][j].setText(" ");
                            resultTextView[i][j].setBackgroundColor(resultColor[colorSetup]);
                            TableRow.LayoutParams params3 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 8f / 24f);
                            resultTextView[i][j].setLayoutParams(params3);
                            holder.resultRows[i].addView(resultTextView[i][j]);
                        }

                }

                for (int i = 0; i < numOfResultRows; i++) {
                    for (int j = 1; j < resultTextView[i].length + 1; j++) {
                        resultTextView[i][j - 1].setText(" ");
                        if (j == result_items.get(position).getResults()[i]) {
                            if (resultTextView[i][j - 1].getText() == " ") {
                                resultTextView[i][j - 1].setText("▼");
                                resultTextView[i][j - 1].setTextColor(Color.WHITE);
                            }

                        }
                    }
                }


                for(int i = 0 ; i<numOfResultRows ; i++){
                    if ( result_items.get(position).getResults()[i] == -100) { // conflict
                        for (int j = 0; j < resultMaximumPoints; j++) {
                            resultTextView[i][j].setBackgroundColor(Color.rgb(0, 0, 0));
                            resultTextView[i][j].setText(" ");
                            resultTextView[i][j].setTextColor(Color.WHITE);
                        }
                        resultTextView[i][9].setText("의");
                        resultTextView[i][10].setText("견");
                        resultTextView[i][11].setText("차");
                        resultTextView[i][12].setText("이");
                    }
                    else if( result_items.get(position).getResults()[i] == -1000) { // not done
                        for (int j = 0; j < resultMaximumPoints; j++) {
                            resultTextView[i][j].setBackgroundColor(Color.rgb(0, 0, 0));
                            resultTextView[i][j].setText(" ");
                            resultTextView[i][j].setTextColor(Color.WHITE);
                        }
                        resultTextView[i][10].setText("미");
                        resultTextView[i][11].setText("완");
                        resultTextView[i][12].setText("료");
                    }
                }



                holder.cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(context, result_items.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(context, DevelInputParent.class);
                        //intent.putExtra("NAME", items.get(position).getName());
                        //intent.putExtra("STATUS",items.get(position).getStatus());
                        //intent.putExtra("ID", items.get(position).getId());
                        //intent.putExtra("MONTH", items.get(position).getMonth());
                        //context.startActivity(intent);
                    }
                });

                break;
        }
    }

    @Override
    public int getItemCount() {
                return this.result_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView q_number;
        TextView q_question;
        ImageView q_section;
        ImageView q_mark;
        CardView cardview;
        CheckBox q_ask_teacher;
        CheckBox q_ask_spouse;

        TableRow[] resultRows = new TableRow[numOfResultRows];
        TextView result_graph_title;

        Button msg_to_spouse;


        public ViewHolder(View itemView) {
            super(itemView);
                    result_graph_title = (TextView) itemView.findViewById(R.id.graph_title);
                    resultRows[0] = (TableRow) itemView.findViewById(R.id.tableRowResult0);
                    resultRows[1] = (TableRow) itemView.findViewById(R.id.tableRowResult1);
                    resultRows[2] = (TableRow) itemView.findViewById(R.id.tableRowResult2);
                    resultRows[3] = (TableRow) itemView.findViewById(R.id.tableRowResult3);
                    resultRows[4] = (TableRow) itemView.findViewById(R.id.tableRowResult4);
                    resultRows[5] = (TableRow) itemView.findViewById(R.id.tableRowResult5);

                    if(isShort){
                        resultRows[5].setVisibility(ProgressBar.GONE);
                    }

                    cardview=(CardView)itemView.findViewById(R.id.cardview);
                    msg_to_spouse = (Button) itemView.findViewById(R.id.button_msg_to_spouse);

        }
    }

    @Override
    public int getItemViewType(int position) {
        /*
        int sum_unknown = 0;
        for(int i=0 ; i < MainActivity.numOfSections ;  i++){
            if( result_items.get(position).getResults()[i] == -1 ){
                sum_unknown = sum_unknown + 1;
            }
        }

        if( sum_unknown == MainActivity.numOfSections)
               return resultConflict;
        else
        */
                return resultGraphView;
    }
}