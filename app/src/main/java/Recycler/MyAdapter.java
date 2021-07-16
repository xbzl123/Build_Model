package Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.root.build_model.R;
import com.example.root.build_model.data.MusicFile;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener,View.OnTouchListener{
    Context context;
    List<MusicFile> list;
    private RecyclerView recyclerView;

    public void setCurTime(int curTime) {
        this.curTime = curTime;
    }

    private int curTime;

    public void setOnChildClickListener(MyAdapter.onChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

    private onChildClickListener onChildClickListener;


    public MyAdapter(Context context, List<MusicFile> list) {
        this.context = context;
        this.list = list;
    }

    public void remove(int pos){
        list.remove(pos);
//        notifyDataSetChanged();
        notifyItemRemoved(pos);
    }
    public void add(int pos,String data){
//        list.add(pos,data);
//        notifyDataSetChanged();
        notifyItemInserted(pos);
    }

    public void change(int pos,String data){
        list.remove(pos);
//        list.add(pos,data);
//        notifyDataSetChanged();
        notifyItemChanged(pos);
    }
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
//        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                view.getViewTreeObserver().removeOnPreDrawListener(this);
//                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//                layoutParams.height = (int) (view.getWidth() / 6);
//                view.setLayoutParams(layoutParams);
//                return true;
//            }
//        });
        view.setOnClickListener(this);
        view.findViewById(R.id.play).setOnClickListener(this);
        view.findViewById(R.id.pause).setOnClickListener(this);
        view.findViewById(R.id.stop).setOnClickListener(this);
        view.findViewById(R.id.play_mode).setOnClickListener(this);
        view.findViewById(R.id.play_seekBar).setOnTouchListener(this);
        view.findViewById(R.id.favorite).setOnClickListener(this);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text.setText(list.get(position).getId()+1+"-"+list.get(position).getName());
        holder.timese.setText(getMusicTime(curTime)+"/"+getMusicTime(list.get(position).getMusicDuration()));
        holder.seekBar.setMax(list.get(position).getMusicDuration()/1000);
        holder.seekBar.setProgress((curTime/1000));
        if(mode % 3 == 1) {
            holder.playmode.setImageResource(R.drawable.ic_repeat_one_black_24dp);
        }else if(mode % 3 == 2){
            holder.playmode.setImageResource(R.drawable.ic_shuffle_black_24dp);
        }else
            holder.playmode.setImageResource(R.drawable.ic_repeat_black_24dp);
        if(position == pos){
            holder.tableRow.setVisibility(View.VISIBLE);
        }else{
            holder.tableRow.setVisibility(View.GONE);
        }
        holder.favourite.setImageResource(list.get(position).getIsFavTime()!=0?
                R.drawable.ic_favorite_black_24dp:R.drawable.ic_favorite_border_black_24dp);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    private int mode = 0;
    @Override
    public void onClick(View v) {
        if(recyclerView != null && onChildClickListener != null){
            if(v.getId() == R.id.test_layout
                    ||v.getId() == R.id.play
                    ||v.getId() == R.id.pause
                    ||v.getId() == R.id.stop
                    ||v.getId() == R.id.play_mode
                    ||v.getId() == R.id.sound_down
                    ||v.getId() == R.id.sound_up
                    ||v.getId() == R.id.favorite
                    ){
                if(v.getId() == R.id.play_mode){
                    mode++;
                }
                onChildClickListener.playClick(v);
            }else{
            pos = recyclerView.getChildAdapterPosition(v);
                if(pos != -1)
                    onChildClickListener.childClick(recyclerView,v,pos,list.get(pos).getName());
            }
        }
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    private int pos = -1;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId() == R.id.play_seekBar){
            onChildClickListener.dragSeekBar(v,event,pos);
        }
        return false;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView text,timese;
        private LinearLayout tableRow;
        private ImageView playmode;
        private SeekBar seekBar;
        private ImageView favourite;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView)itemView.findViewById(R.id.text);
            tableRow = (LinearLayout)itemView.findViewById(R.id.player_bar);
            timese = (TextView)itemView.findViewById(R.id.timese);
            playmode = (ImageView)itemView.findViewById(R.id.play_mode);
            seekBar = (SeekBar)itemView.findViewById(R.id.play_seekBar);
            favourite = (ImageView)itemView.findViewById(R.id.favorite);
        }
    }
    public interface onChildClickListener{
        void childClick(RecyclerView recyclerView,View view,int pos,String data);
        void playClick(View v);
        void dragSeekBar(View view,MotionEvent event,int pos);
    }
    private String getMusicTime(int duration){
        SimpleDateFormat sdf=new SimpleDateFormat("mm:ss");
        return sdf.format(duration);
    }


}
