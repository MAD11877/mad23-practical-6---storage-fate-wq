package sg.edu.np.mad.week2t04.practical3;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_DEFAULT = 0;
    private static final int VIEW_TYPE_SPECIAL = 1;

    private List<User> userList = new ArrayList<>();
    private List<Integer> randomNumbers = new ArrayList<>();
    private OnItemClickListener listener;


    public void setUserList(List<User> userList) {
        this.userList = userList;
        generateRandomNumbers();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public User getUser(int position) {
        return userList.get(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_SPECIAL) {
            View view = inflater.inflate(R.layout.item_user_7, parent, false);
            return new SpecialViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_user, parent, false);
            return new DefaultViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        User user = userList.get(position);
        int randomNumber = randomNumbers.get(position);

        if (viewHolder instanceof SpecialViewHolder) {
            SpecialViewHolder holder = (SpecialViewHolder) viewHolder;
            // Bind data to views in the special layout
            holder.textViewName.setText(user.getName() + "-" + randomNumber);
            holder.textViewDescription.setText(user.getDescription());
        } else if (viewHolder instanceof DefaultViewHolder) {
            DefaultViewHolder holder = (DefaultViewHolder) viewHolder;
            // Bind data to views in the default layout
            holder.textViewName.setText(user.getName() + "-" + randomNumber);
            holder.textViewDescription.setText(user.getDescription());
        }
        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int lastDigit = randomNumbers.get(position) % 10;
        return (lastDigit == 7) ? VIEW_TYPE_SPECIAL : VIEW_TYPE_DEFAULT;
    }

    private void generateRandomNumbers() {
        randomNumbers.clear();
        Random random = new Random();
        for (int i = 0; i < userList.size(); i++) {
            int randomNumber = random.nextInt(900000000) + 100000000;
            randomNumbers.add(randomNumber);
        }
    }

    public class DefaultViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewDescription;

        public DefaultViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);

            // Set click listener for the item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    public class SpecialViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewDescription;

        public SpecialViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);

            // Set click listener for the item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public int getRandomNumber(User user) {
        int position = userList.indexOf(user);
        if (position != -1 && position < randomNumbers.size()) {
            return randomNumbers.get(position);
        }
        return 0; // Default value if user or random number is not found
    }
}