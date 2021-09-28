package com.itsram.quizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.viewholder> {
    private List<QuestionModel> list;

    public ScoreAdapter(List<QuestionModel> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public ScoreAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.save_item,parent,false);
        return new ScoreAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.setData(list.get(position).getQuestion(),
                list.get(position).getCorrectAns(),
                list.get(position).getOptionA(),
                list.get(position).getOptionB(),
                list.get(position).getOptionC(),
                list.get(position).getOptionD(),position);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
        private TextView question,optionA,optionB,optionC,optionD,answer;
        //  private ImageButton deleteBtn;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            question=itemView.findViewById(R.id.question);
            answer=itemView.findViewById(R.id.answer);
            optionA=itemView.findViewById(R.id.optionA);
            optionB=itemView.findViewById(R.id.optionB);
            optionC=itemView.findViewById(R.id.optionC);
            optionD=itemView.findViewById(R.id.optionD);
        }


        private void setData(String question, String answer,String optionA,String optionB,String optionC,String optionD,final int position)
        {
            this.question.setText("Q. "+question);
            this.answer.setText("✓. "+answer);
            this.optionA.setText("A. "+optionA);
            this.optionB.setText("B. "+optionB);
            this.optionC.setText("C. "+optionC);
            this.optionD.setText("D. "+optionD);
           /* deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    notifyItemRemoved(position);
                }
            });*/
        }
    }
}

