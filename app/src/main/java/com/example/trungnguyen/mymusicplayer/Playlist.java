package com.example.trungnguyen.mymusicplayer;

import com.example.trungnguyen.mymusicplayer.models.Artist;
import com.example.trungnguyen.mymusicplayer.models.Song;
import com.example.trungnguyen.mymusicplayer.models.TopTracks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung Nguyen on 11/5/2016.
 */
public class Playlist {
    public static Song[] songs = {
            new Song("https://parsefiles.back4app.com/cXhfBZUu1YgwlTHw97njIaDtIzcXdE3elqiVFghe/83fa1afba45ff992d714c3027a470584_audio.mp3", 1, "Anh Cứ Đi Đi", 180, "Thái Tuyết Trâm", "Label1", 2012, 100, false),
            new Song("https://parsefiles.back4app.com/cXhfBZUu1YgwlTHw97njIaDtIzcXdE3elqiVFghe/492ca9140be92c7de5ff3e435ac3f3e9_audio.mp3", 2, "Cho Anh Gần Em Thêm Chút Nữa", 190, "Hương Tràm", "Label2", 2005, 101, false),
            new Song("https://parsefiles.back4app.com/cXhfBZUu1YgwlTHw97njIaDtIzcXdE3elqiVFghe/ff790971155f13222a0e3dcc04f2910a_audio.mp3", 3, "Mơ", 200, "Đen ft Hậu Vi", "Label3", 2002, 102, false),
            new Song("http://zmp3-mp3-s1.zmp3-bdhcm-1.za.zdn.vn/6848a9e333a7daf983b6/6214618151873325179?key=Yk_nsEpgjkeO6m0aJ445wg&expires=1479317927", 4, "Mình Là Gì Của Nhau", 210, "Lou Hoang", "Label4", 2013, 103, false),
            new Song("http://s1.mp3.zdn.vn/357ceb7172359b6bc224/4706531562149718908?key=bflBhoIoAGH5diLiOJ9kdw&expires=1479316645", 5, "Gương Mặt Lạ Lẫm", 220, "Mr.Siro", "Label5", 1987, 104, false),
            new Song("http://zmp3-mp3-s1.r.za.zdn.vn/1e5739bfa2fb4ba512ea/5530946653574441023?key=yN9G6FHDUR_PvbeoeZAU4A&expires=1479317158", 6, "Quá Khứ Còn Lại Gì", 230, "Rocker Nguyen", "Label6", 2013, 105, false),
            new Song("http://zmp3-mp3-s1.r.za.zdn.vn/8b821884bfc0569e0fd1/2479470724047784515?key=3wUXeB5qspYr8VjrQ8in-w&expires=1479318303", 7, "Mưa Hồng", 240, "Bang Kieu ft Tuan Ngoc", "Disney", 2014, 106, false),
            new Song("http://s1.mp3.zdn.vn/9b2ec3735937b069e926/4516422587302199318?key=6HevagjN9iGQKmq5RGKTYQ&expires=1479318035", 8, "Em Đừng Thả Thính", 240, "Nhat Anh ft Vanh Leg", "Vietnam", 2016, 107, false),
            new Song("http://zmp3-mp3-s1.r.za.zdn.vn/a0ad4e5ddb1932476b08/6032808279743179800?key=yl7I_8zKNhI5H5xJa3mqAg&expires=1479318046", 9, "Yêu Em", 240, "Rocker Nguyen", "Vietnam", 2016, 107, false),
            new Song("http://zmp3-mp3-s1.r.za.zdn.vn/dbd3aeac37e8deb687f9/4385877938719831814?key=lsbY4ThgDnYtF61IJ_cAoQ&expires=1479318389", 10, "Loneliness", 240, "Loneliness", "Vietnam", 2016, 107, false),
            new Song("http://s1.mp3.zdn.vn/ef9ff4376d73842ddd62/6966070489983947677?key=0mq93qot4M73u2lcUp47Rg&expires=1479318329", 11, "Từng Ngày Em Mơ Về Anh", 240, "MLee - Soobin Hoang Son", "Vietnam", 2016, 107, false)
    };

    public static List<TopTracks> topTracks;

    public static List<TopTracks> searchingTracks;

    public static List<Artist> artists;
}
