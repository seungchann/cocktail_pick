package com.example.cocktail_pick.MyPageTab;

import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cocktail_pick.HomeTab.RecipeTagAdapter;
import com.example.cocktail_pick.Main.MainViewModel;
import com.example.cocktail_pick.Main.MainViewModelFactory;
import com.example.cocktail_pick.MainRepository;
import com.example.cocktail_pick.Member;
import com.example.cocktail_pick.R;
import com.example.cocktail_pick.RetrofitService;
import com.example.cocktail_pick.User;
import com.example.cocktail_pick.UserReceive;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MyPageTabFragment extends Fragment {
    RecyclerView my_tag_recycler_view;
    LinearLayout whole_view;
    ImageView my_profile_image_view;
    TextView my_name_text_view;
    List<String> testTagList = new ArrayList<>();
    User my_user = new User("@.","user","", testTagList);
    CartFragment cartFragment;
    MyRecipeFragment myRecipeFragment;
    RetrofitService retrofitService = RetrofitService.Companion.getInstance();
    MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity(), new MainViewModelFactory(new MainRepository(retrofitService))).get(MainViewModel.class);
        viewModel.loadCurrentAccount();

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);
        my_tag_recycler_view = rootView.findViewById(R.id.my_tag_recycler_view);
        my_tag_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        my_tag_recycler_view.post(new Runnable() {
            @Override
            public void run() {
                my_tag_recycler_view.smoothScrollBy(0, 800);
            }
        });
        my_tag_recycler_view.setAdapter(new RecipeTagAdapter(getActivity()));

        my_profile_image_view = rootView.findViewById(R.id.my_profile_image_view);
        my_name_text_view = rootView.findViewById(R.id.my_name_text_view);

        viewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<List<UserReceive>>() {
            @Override
            public void onChanged(List<UserReceive> userReceives) {
                my_name_text_view.setText(userReceives.get(0).getUserName());
                Glide.with(getActivity()).load(userReceives.get(0).getProfileURL()).into(my_profile_image_view);
            }
        });
        whole_view = rootView.findViewById(R.id.my_page_whole_view);
//        whole_view.setBackgroundColor(); TODO!!! 태그 색깔로 배경 색깔 바꿔주기..!

        cartFragment = new CartFragment();
        myRecipeFragment = new MyRecipeFragment();

        TabLayout tabLayout = rootView.findViewById(R.id.mypage_tab_layout);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mypage_fragment_layout, cartFragment).commit();
        tabLayout.addTab(tabLayout.newTab().setText("찜한 레시피"));
        tabLayout.addTab((tabLayout.newTab().setText("나의 레시피")));
        tabLayout.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                float cornerRadiusDP = 16f;
                float cornerRadius = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, cornerRadiusDP, getResources().getDisplayMetrics());
                outline.setRoundRect(0, 0, view.getWidth(), (int)(view.getHeight() + cornerRadius), cornerRadius);
            }
        });
        tabLayout.setClipToOutline(true);
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container)
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if (position == 0) {
                    selected = cartFragment;
                } else if (position == 1) {
                    selected = myRecipeFragment;
                }

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mypage_fragment_layout, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return rootView;
    }
}
