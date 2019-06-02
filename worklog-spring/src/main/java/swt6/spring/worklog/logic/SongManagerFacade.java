package swt6.spring.worklog.logic;

import swt6.spring.worklog.domain.Album;
import swt6.spring.worklog.domain.Genre;
import swt6.spring.worklog.domain.Song;

import java.util.List;

public interface SongManagerFacade {

    public Song         syncSong(Song song);
    public Song         findSongById(Long id);
    public List<Song>   findAllSongs();
    public void         deleteSong(Long id);

    public Album        syncAlbum(Album album);
    public Album        findAlbumById(Long id);
    public List<Album>  findAllAlbums();
    public void         deleteAlbum(Long id);

    public Genre        syncGenre(Genre genre);
    public Genre        findGenreById(Long id);
    public List<Genre>  findAllGenres();
    public void         deleteGenre(Long id);
}
