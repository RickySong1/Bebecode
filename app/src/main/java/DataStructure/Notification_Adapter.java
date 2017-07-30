package DataStructure;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaist.supersong.bebecode.ProgressStatus;
import com.kaist.supersong.bebecode.R;

/**
 * UserAdapter - Custom ListView를 구현하기 위해 하나의 아이템 정보와 레이아웃을 가져와서 합친다.
 *
 * @author Cloud Travel
 */
public class Notification_Adapter extends BaseAdapter implements OnClickListener{

    // Activity에서 가져온 객체정보를 저장할 변수
    private Notification_msg mUser;
    private Context mContext;

    // ListView 내부 View들을 가르킬 변수들
    private ImageView imgUserIcon;
    private TextView tvUserName;
    private TextView tvUserPhoneNumber;


    private Bitmap noti_father;
    private Bitmap noti_mother;
    private Bitmap noti_img;
    private Bitmap noti_love;
    private Bitmap noti_baby;



    // 리스트 아이템 데이터를 저장할 배열
    private ArrayList<Notification_msg> mUserData;

    public Notification_Adapter(Context context) {
        super();
        mContext = context;
        Resources mResources= mContext.getResources();;

        mUserData = new ArrayList<Notification_msg>();

        noti_father = BitmapFactory.decodeResource(mResources, R.drawable.noti_father);
        noti_mother = BitmapFactory.decodeResource(mResources, R.drawable.noti_mother);
        noti_img = BitmapFactory.decodeResource(mResources, R.drawable.noti_img);
        noti_love = BitmapFactory.decodeResource(mResources, R.drawable.noti_love);
        noti_baby = BitmapFactory.decodeResource(mResources, R.drawable.noti_baby);

    }

    @Override
    /**
     * @return 아이템의 총 개수를 반환
     */
    public int getCount() {
        // TODO Auto-generated method stub
        return mUserData.size();
    }

    @Override
    /**
     * @return 선택된 아이템을 반환
     */
    public Notification_msg getItem(int position) {
        // TODO Auto-generated method stub
        return mUserData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }


    static class CellHolder
    {
        ImageView userImage;
        TextView msg;
    }


    @Override
    /**
     * getView
     *
     * @param position - 현재 몇 번째로 아이템이 추가되고 있는지 정보를 갖고 있다.
     * @param convertView - 현재 사용되고 있는 어떤 레이아웃을 가지고 있는지 정보를 갖고 있다.
     * @param parent - 현재 뷰의 부모를 지칭하지만 특별히 사용되지는 않는다.
     * @return 리스트 아이템이 저장된 convertView
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;

        final CellHolder holder;

        // 리스트 아이템이 새로 추가될 경우에는 v가 null값이다.
        // view는 어느 정도 생성된 뒤에는 재사용이 일어나기 때문에 효율을 위해서 해준다.
        if (v == null) {
            // inflater를 이용하여 사용할 레이아웃을 가져옵니다.
            //v =  ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.notification_listview_item, null);
            v =  LayoutInflater.from(mContext).inflate(R.layout.notification_listview_item,null);
            holder = new CellHolder();
            holder.userImage = (ImageView) v.findViewById(R.id.user_icon);
            holder.msg = (TextView) v.findViewById(R.id.notification_text);
            v.setTag(holder);
        } else{
            holder = (CellHolder)v.getTag();
        }
        // 받아온 position 값을 이용하여 배열에서 아이템을 가져온다.
        mUser = getItem(position);

        // Tag를 이용하여 데이터와 뷰를 묶습니다.
        //btnSend.setTag(mUser);

        // 데이터의 실존 여부를 판별합니다.
        if ( mUser != null ){
            // 데이터가 있다면 갖고 있는 정보를 뷰에 알맞게 배치시킵니다.
            if(mUser.getmImgType().contains(ProgressStatus.IMG_FATHER))
                holder.userImage.setImageBitmap(noti_father);
            else if(mUser.getmImgType().contains(ProgressStatus.IMG_MOTHER))
                holder.userImage.setImageBitmap(noti_mother);
            else if(mUser.getmImgType().contains(ProgressStatus.IMG_IMG))
                holder.userImage.setImageBitmap(noti_img);
            else if(mUser.getmImgType().contains(ProgressStatus.IMG_LOVE))
                holder.userImage.setImageBitmap(noti_love);
            else if(mUser.getmImgType().contains(ProgressStatus.IMG_BABY))
                holder.userImage.setImageBitmap(noti_baby);
            else
                holder.userImage.setImageBitmap(noti_baby);
            holder.msg.setText(mUser.getmMsg());
            //btnSend.setOnClickListener(this);
        }

        // 완성된 아이템 뷰를 반환합니다.
        return v;
    }

    // 데이터를 추가하는 것을 위해서 만들어 준다.
    public void add(Notification_msg user){
        mUserData.add(user);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        // Tag를 이용하여 Data를 가져옵니다.
        Notification_msg clickItem = (Notification_msg)v.getTag();

        /*
        switch (v.getId()){
            case R.id.btn_send:
                Toast.makeText(mContext, clickItem.getmUserName() , Toast.LENGTH_SHORT).show();
                break;
        }
        */
    }
}
