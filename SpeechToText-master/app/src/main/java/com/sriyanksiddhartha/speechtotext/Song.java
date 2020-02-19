package com.sriyanksiddhartha.speechtotext;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private List<String> song;

    public Song(String word)
    {
        this.song = new ArrayList<>();
        String[] words = word.split(" ");
        for(int i = 0; i < words.length; i++)
        {
            this.song.add(words[i]);
        }
    }

    public List<String> getSong()
    {
        return this.song;
    }
}
