package swt6.spring.worklog.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Genre implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToMany(mappedBy = "genre",
            cascade= {CascadeType.PERSIST, CascadeType.MERGE},
            fetch=FetchType.LAZY)
    private Set<Song> songs = new HashSet<>();

    public Genre() {}

    public Genre(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void addSong(Song song) {
        if (song != null) {
            if (song.getGenre() != null)
                song.getGenre().getSongs().remove(song);
            song.setGenre(this);
            songs.add(song);
        }
    }

    public void removeSong(Song song) {
        if (song != null) {
            if (song.getGenre().equals(this)) {
                song.setGenre(null);
                songs.remove(song);
            }
        }
    }
}
