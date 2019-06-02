package swt6.spring.worklog.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Song implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String title;
    private Long duration;
    private String interpret;

    @ManyToMany(mappedBy = "songs",
            cascade= CascadeType.ALL,
            fetch=FetchType.EAGER)
    private Set<Album> albums = new HashSet<>();

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch=FetchType.EAGER)
    private Genre genre;

    public Song() {
        addGenre(new Genre("Undefined"));
    }

    public Song(String title, Long duration, String interpret) {
        this.title = title;
        this.duration = duration;
        this.interpret = interpret;
        addGenre(new Genre("Undefined"));
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

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getInterpret() {
        return interpret;
    }

    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void addGenre(Genre genre){
        if (this.genre != null) {
            this.genre.getSongs().remove(this);
        }

        if (genre != null) {
            genre.addSong(this);
            this.genre = genre;
        }
    }

    public void removeGenre(Genre genre) {
        if (genre != null) {
            genre.getSongs().remove(this);
            this.genre = null;
        }
    }

    public void addAlbum(Album album) {
        if (album != null) {
            album.getSongs().add(this);
            albums.add(album);
        }
    }

    public void removeAlbum(Album album) {
        if (album != null) {
            album.getSongs().remove(this);
            albums.remove(album);
        }
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", Interpret='" + interpret + '\'' +
                '}';
    }
}
