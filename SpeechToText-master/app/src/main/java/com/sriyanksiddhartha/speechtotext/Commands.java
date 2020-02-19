package com.sriyanksiddhartha.speechtotext;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Commands {
    private List<Command> commands;
    private List<String> actualCommand;
    private List<String> song;
    private List<Integer> commandIndices;
    private List<Integer> musicIndices;
    private boolean isNeededArgument;
    private boolean isValidCommandFlag;
    private boolean isValidSongFlag;
    private List<Song> songs;

    public Commands()
    {
        this.isValidCommandFlag = false;
        this.actualCommand = null;
        this.isNeededArgument = false;
        this.isValidSongFlag = false;
        this.musicIndices = new ArrayList<>();
        this.commandIndices = new ArrayList<>();
        this.song = new ArrayList<>();
        this.commands = new ArrayList<>();
        this.commands.add(new Command("Skończ", false));
        this.commands.add(new Command("Stop", false));
        this.commands.add(new Command("Zacznij", false));
        this.commands.add(new Command("Start", false));
        this.commands.add(new Command("Następny", false));
        this.commands.add(new Command("Next", false));
        this.commands.add(new Command("Poprzedni", false));
        this.commands.add(new Command("Previous", false));
        this.commands.add(new Command("Głośniej", false));
        this.commands.add(new Command("Louder", false));
        this.commands.add(new Command("Ciszej", false));
        this.commands.add(new Command("Quieter", false));
        this.commands.add(new Command("Wyłącz się", false));
        this.commands.add(new Command("Turn off", false));
        this.commands.add(new Command("Dodaj piosenkę do ulubionych", false));
        this.commands.add(new Command("Add song to favorites", false));
        this.commands.add(new Command("Odtwórz ulubione", false));
        this.commands.add(new Command("Play favorite", false));
        this.commands.add(new Command("Odtwórz album", false));
        this.commands.add(new Command("Play album", false));
        this.commands.add(new Command("Odtwórz", true));
        this.commands.add(new Command("Play", true));
        this.commands.add(new Command("Znajdź", true));
        this.commands.add(new Command("Find", true));
        this.songs = new ArrayList<>();
        this.songs.add(new Song("Długość dźwięku samotności"));
        this.songs.add(new Song("The Length of the Solitude Sound"));
        this.songs.add(new Song("Bailando"));
        this.songs.add(new Song("The Final Countdown"));
        this.songs.add(new Song("Show"));
        this.songs.add(new Song("Last Christmas"));
    }

    public List<Command> getCommands()
    {
        return this.commands;
    }

    public String getActualCommand()
    {
        return this.actualCommand.toString().replaceAll(",", "");
    }

    public void addWord(String word) {

        //locals
        List<String> tempList;
        List<String> tempSong;
        int index;

        if(this.actualCommand == null)
        {
            this.actualCommand = new ArrayList<>();
            this.actualCommand.add(word);
            for(int i = 0; i < this.commands.size(); i++)
            {
                tempList = this.commands.get(i).getCommand();
                if(tempList.size() == 1)
                {
                    if(this.actualCommand.get(0).equalsIgnoreCase(tempList.get(0)))
                    {
                        this.isValidCommandFlag = true;
                        this.isNeededArgument = this.commands.get(i).isNeededArgument();
                        this.commandIndices.add(i);
                        break;
                    }
                }
                else
                {
                    if(this.actualCommand.get(0).equalsIgnoreCase(tempList.get(0)))
                    {
                        this.commandIndices.add(i);
                    }
                }
            }
        }
        else
        {
            if(this.isValidCommand() == false)
            {
                this.actualCommand.add(word);
                for(int i = 0; i < this.commandIndices.size(); i++)
                {
                    index = this.commandIndices.get(i);
                    tempList = this.commands.get(index).getCommand();

                    if(tempList.size() == this.actualCommand.size())
                    {
                        if(this.actualCommand.get(this.actualCommand.size() - 1).equalsIgnoreCase(tempList.get(this.actualCommand.size() - 1)))
                        {
                            this.isValidCommandFlag = true;
                            this.isNeededArgument = this.commands.get(index).isNeededArgument();
                            this.commandIndices.clear();
                            this.commandIndices.add(index);
                            break;
                        }
                        else
                        {
                            this.commandIndices.remove(i);
                            i--;
                        }
                    }
                    else if(tempList.size() > this.actualCommand.size())
                    {
                        if(this.actualCommand.get(this.actualCommand.size() - 1).equalsIgnoreCase(tempList.get(this.actualCommand.size() - 1)))
                        {
                            //this.commandIndices.add(index);
                        }
                        else
                        {
                            this.commandIndices.remove(i);
                            i--;
                        }
                    }
                    else
                    {
                        this.commandIndices.remove(i);
                        i--;
                    }
                }
            }
            else if(this.isNeededArgument == true)
            {
                if (this.song.size() == 0)
                {
                    this.song.add(word);
                    for(int i = 0; i < this.songs.size(); i++)
                    {
                        tempSong = this.songs.get(i).getSong();
                        if(tempSong.size() == 1)
                        {
                            if(this.song.get(0).equalsIgnoreCase(tempSong.get(0)))
                            {
                                this.isValidSongFlag = true;
                                this.musicIndices.add(i);
                                break;
                            }
                        }
                        else
                        {
                            if(this.song.get(0).equalsIgnoreCase(tempSong.get(0)))
                            {
                                this.musicIndices.add(i);
                            }
                        }
                    }
                }
                else
                {
                    this.song.add(word);
                    for(int i = 0; i < this.musicIndices.size(); i++)
                    {
                        index = this.musicIndices.get(i);
                        tempSong = this.songs.get(index).getSong();

                        if(tempSong.size() == this.song.size())
                        {
                            if(this.song.get(this.song.size() - 1).equalsIgnoreCase(tempSong.get(this.song.size() - 1)))
                            {
                                this.isValidSongFlag = true;
                                this.musicIndices.clear();
                                this.musicIndices.add(index);
                                break;
                            }
                            else
                            {
                                this.musicIndices.remove(i);
                                i--;
                                if(this.musicIndices.size() == 0)
                                {
                                    this.isValidSongFlag = false;
                                }
                            }
                        }
                        else

                            if(tempSong.size() > this.song.size())
                        {
                            if(this.song.get(this.song.size() - 1).equalsIgnoreCase(tempSong.get(this.song.size() - 1)))
                            {
                                //this.musicIndices.add(i);
                            }
                            else
                            {
                                this.musicIndices.remove(i);
                                i--;
                                if(this.musicIndices.size() == 0)
                                {
                                    this.isValidSongFlag = false;
                                }
                            }
                        }
                        else
                        {
                            this.musicIndices.remove(i);
                            i--;
                            if(this.musicIndices.size() == 0)
                            {
                                this.isValidSongFlag = false;
                            }
                        }
                    }
                }
            }
        }

        if(this.commandIndices.size() == 0 || (this.musicIndices.size() == 0 && this.isNeededArgument == true && this.isValidSong() == false && this.song.size() != 0))
        {
            this.actualCommand = null;
        }
    }

    public boolean isValidCommand()
    {
        return this.isValidCommandFlag;
    }

    public boolean unrecognizedCommand()
    {
        return this.actualCommand == null;
    }

    public void resetActualCommand()
    {
        this.commandIndices.clear();
        this.actualCommand = null;
        this.isValidCommandFlag = false;
        this.isNeededArgument = false;
        this.song.clear();
        this.isValidSongFlag = false;
        this.musicIndices.clear();

    }

    public String getSong()
    {
        return this.song.toString().replaceAll(",", "");
    }

    public boolean isValidExpression()
    {
        if(this.isValidCommand() == true && this.isValidSongFlag == true && this.isNeededArgument == true)
        {
            return true;
        }
        else return this.isValidCommand() == true && this.isNeededArgument == false;
    }

    public boolean isValidSong()
    {
        return this.isValidSongFlag;
    }

    public String getString()
    {
        return this.isNeededArgument + " " + this.song.toString() + " " + this.isValidCommand() + " " +
                this.musicIndices.toString() + " " + this.commandIndices.toString() + " ";
    }
}
