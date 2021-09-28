package com.itsram.quizapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class TheoryAdapter extends RecyclerView.Adapter<TheoryAdapter.myviewHolder> {

    private List<TheoryModel> theoryModelList;

    public TheoryAdapter(List<TheoryModel> theoryModelList) {
        this.theoryModelList = theoryModelList;
    }


    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_theory,parent,false);
    return new myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        holder.setData(theoryModelList.get(position).getFilename(),theoryModelList.get(position).getFileurl());
    }

    @Override
    public int getItemCount() {
        return theoryModelList.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        TextView filename;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            filename=itemView.findViewById(R.id.filename);

        }
        private void setData(final String filename, final String fileurl){

            this.filename.setText(filename);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent setIntent=new Intent(itemView.getContext(),PdfViewActivity.class);
                    setIntent.putExtra("filename",filename);
                    setIntent.putExtra("fileurl",fileurl);
                    itemView.getContext().startActivity(setIntent);
                }
            });
        }
    }


}
