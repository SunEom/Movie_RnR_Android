package io.github.suneom.MovieRnR;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {

    MovieAdapter adapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView_home);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = new MovieAdapter();

        adapter.addItem(new Movie("국제수사","Action, Drama", "필리핀으로 인생 첫 해외여행을 떠난 대천경찰서 강력팀 ‘홍병수' 경장. 여행의 단꿈도 잠시, ‘병수’는 범죄 조직 킬러 ‘패트릭’의 셋업 범죄에 휘말려 살인 용의자가 되고, 누명을 벗기 위해 현지 가이드이자 고향 후배 ‘만철’과 함께 수사에 나선다. 하지만, 형사 본능이 끓어오르는 마음과는 달리 ’병수’의 몸과 영어는 따라주지 않고, 필리핀에서 재회한 웬수 같은 죽마고우 ‘용배’가 끼어드는 바람에 수사는 자꾸 의도치 않은 방향으로 흘러가는데… 촌구석 형사 ‘병수’는 과연 무사히 한국에 돌아갈 수 있을까?", 7,
                5));
        adapter.addItem(new Movie("국제수사","Action, Drama", "필리핀으로 인생 첫 해외여행을 떠난 대천경찰서 강력팀 ‘홍병수' 경장. 여행의 단꿈도 잠시, ‘병수’는 범죄 조직 킬러 ‘패트릭’의 셋업 범죄에 휘말려 살인 용의자가 되고, 누명을 벗기 위해 현지 가이드이자 고향 후배 ‘만철’과 함께 수사에 나선다. 하지만, 형사 본능이 끓어오르는 마음과는 달리 ’병수’의 몸과 영어는 따라주지 않고, 필리핀에서 재회한 웬수 같은 죽마고우 ‘용배’가 끼어드는 바람에 수사는 자꾸 의도치 않은 방향으로 흘러가는데… 촌구석 형사 ‘병수’는 과연 무사히 한국에 돌아갈 수 있을까?", 7,
                5));
        adapter.addItem(new Movie("국제수사","Action, Drama", "필리핀으로 인생 첫 해외여행을 떠난 대천경찰서 강력팀 ‘홍병수' 경장. 여행의 단꿈도 잠시, ‘병수’는 범죄 조직 킬러 ‘패트릭’의 셋업 범죄에 휘말려 살인 용의자가 되고, 누명을 벗기 위해 현지 가이드이자 고향 후배 ‘만철’과 함께 수사에 나선다. 하지만, 형사 본능이 끓어오르는 마음과는 달리 ’병수’의 몸과 영어는 따라주지 않고, 필리핀에서 재회한 웬수 같은 죽마고우 ‘용배’가 끼어드는 바람에 수사는 자꾸 의도치 않은 방향으로 흘러가는데… 촌구석 형사 ‘병수’는 과연 무사히 한국에 돌아갈 수 있을까?", 7,
                5));



        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
