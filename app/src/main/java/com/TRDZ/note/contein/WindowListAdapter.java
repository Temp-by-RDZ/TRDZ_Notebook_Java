package com.TRDZ.note.contein;

import static com.TRDZ.note.MainActivity.data;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TRDZ.note.R;

public class WindowListAdapter extends RecyclerView.Adapter<WindowListAdapter.MyHolder> {

	private WindowsListIteration iteration;

	public void set_Iteration(WindowsListIteration iteration) {
		this.iteration = iteration;
		}

	@NonNull
	@Override
	public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		return new MyHolder(inflater.inflate(R.layout.win_list_item, parent, false));
		}

	@Override
	public void onBindViewHolder(@NonNull MyHolder holder, int position) {
		holder.set_content(position);
		}

	@Override
	public int getItemCount() {
		return data.Size();
		}

	@SuppressLint("NotifyDataSetChanged")
	public void sort() {
		data.sort();
		notifyDataSetChanged();
		}

	@SuppressLint("NotifyDataSetChanged")
	public void reload() {
		notifyDataSetChanged();
		}

	public void change(int index, boolean is_new) {
		if (is_new) notifyItemRangeInserted(data.Size() - 1, 1);
		else notifyItemChanged(index);
		}

	public void remove(int index) {
		data.remove(index);
		notifyItemRemoved(index);
		}

	class MyHolder extends RecyclerView.ViewHolder {
		private final TextView L_state;

		public MyHolder(@NonNull View itemView) {
			super(itemView);
			LinearLayout line = (LinearLayout) itemView.findViewById(R.id.list_block);
			L_state = line.findViewById(R.id.T_name);
			L_state.setOnClickListener(view -> { if (iteration != null) iteration.OnClick_create_info(getLayoutPosition()); });
			L_state.setOnLongClickListener(v -> { if (iteration != null) iteration.OnPress_get_iteration(getLayoutPosition(), v);return true; });
			}

		public void set_content(int number) {
			L_state.setText(data.get_line(number));
			}
		}
	}
