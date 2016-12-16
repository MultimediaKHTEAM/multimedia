package com.example.trungnguyen.mymusicplayer.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.trungnguyen.mymusicplayer.MainActivity;
import com.example.trungnguyen.mymusicplayer.Playlist;
import com.example.trungnguyen.mymusicplayer.R;
import com.example.trungnguyen.mymusicplayer.adapters.SearchingListAdapter;
import com.example.trungnguyen.mymusicplayer.models.AllSong;
import com.example.trungnguyen.mymusicplayer.models.TopTracks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Trung Nguyen on 12/16/2016.
 */
public class SearchingFragment extends Fragment {
    private static final String TAG = SearchingFragment.class.getSimpleName();
    ListView lvSearchingAllSong;
    public static final String jsonUrl = "http://mp3.zing.vn/json/song/get-source/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.searching_fragment, container, false);
        lvSearchingAllSong = (ListView) mView.findViewById(R.id.lvSeachingAllSong);
        String url = "http://mp3.zing.vn/tim-kiem/bai-hat.html?q=";
        String query = getArguments().getString("HTML_REQUEST");
        if (query.contains(" ")) {
            query.trim();
            while (query.indexOf("  ") != -1)
                query = query.replaceAll("  ", " ");
            while (query.indexOf(" ") != -1)
                query = query.replaceAll(" ", "+");
        }
        new RequestTask().execute(url + query);
        SearchingListAdapter adapter = new SearchingListAdapter(getActivity(), android.R.layout.simple_list_item_1, Playlist.allSongs);
        lvSearchingAllSong.setAdapter(adapter);
        lvSearchingAllSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(jsonUrl + Playlist.allSongs.get(pos).getSourceList()).build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(jsonData);
                                JSONArray data = jsonObject.getJSONArray("data");
                                JSONObject getData = data.getJSONObject(0);
                                String name = getData.getString("name");
                                String artist = getData.getString("artist");
                                String cover = getData.getString("cover");
                                JSONArray source_list = getData.getJSONArray("source_list");
                                String playSongUrl = source_list.getString(0);
                                Playlist.tracks[pos] = new TopTracks(playSongUrl, name, artist, cover, false);
                                Log.d(TAG, playSongUrl);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
                Animation effectOnclickAnimation = new AlphaAnimation(0.3f, 1.0f);
                effectOnclickAnimation.setDuration(500);
                view.startAnimation(effectOnclickAnimation);
                Log.d(TAG, "onItemClick");
                Bundle bundle = new Bundle();
                bundle.putParcelable(MainActivity.SONG_NAME, Playlist.tracks[pos]);
                Log.d(TAG, pos + "");
                bundle.putInt(MainActivity.LIST_POSITION, pos);
                nowPlayingFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.childPlaceHolder, nowPlayingFragment, MainActivity.FRAGMENT_NOW_PLAYING);
                fragmentTransaction.commit();
            }
        });
        return mView;
    }

    public class RequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            Document doc = null;
            Playlist.allSongs = new ArrayList<>();
            try {
                doc = Jsoup.connect(url).get();
                Elements elements = doc.select("div.item-song");
                if (elements != null && elements.size() > 0) {
                    for (Element element : elements) {
                        String dataCode = element.attr("data-code");
                        String title = element.getElementsByClass("title-song").text();
                        Playlist.allSongs.add(new AllSong(dataCode, title));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new String();
        }
    }
}
