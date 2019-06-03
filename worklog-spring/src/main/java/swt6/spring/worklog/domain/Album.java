package swt6.spring.worklog.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Album implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String title;
    @Column(name = "releaseYear")
    private int year;

    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE},
            fetch=FetchType.EAGER)
    @JoinTable(name="AlbumSongs",
    joinColumns = {@JoinColumn(name="albumId")},
    inverseJoinColumns = {@JoinColumn(name = "songId")})
    private Set<Song> songs = new HashSet<>();

    public Album() {}

    public Album(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song) {
        if(song != null) {
            song.getAlbums().add(this);
            songs.add(song);
        }
    }

    public void removeSong(Song song) {
        if (song != null) {
            song.getAlbums().remove(this);
            songs.remove(song);
        }
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", songs=" + songs +
                '}';
    }
}
