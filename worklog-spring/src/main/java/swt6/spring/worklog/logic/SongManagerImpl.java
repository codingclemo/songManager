package swt6.spring.worklog.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.AlbumRepository;
import swt6.spring.worklog.dao.GenreRepository;
import swt6.spring.worklog.dao.SongRepository;
import swt6.spring.worklog.domain.Album;
import swt6.spring.worklog.domain.Genre;
import swt6.spring.worklog.domain.Song;

import java.util.List;
import java.util.Set;

@Component("songManager")
@Primary
@Transactional
public class SongManagerImpl implements SongManagerFacade {


    @Autowired
    private SongRepository songRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AlbumRepository albumRepository;

    public void setSongRepository(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public void setAlbumRepository(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public Song syncSong(Song song) {
        return songRepository.saveAndFlush(song);
//        return songRepository.save(song);
    }

    @Override
    public Song findSongById(Long id) {
        return songRepository.findById(id).get();
    }

    @Override
    public List<Song> findAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public void deleteSong(Long id) {
        Song song = findSongById(id);
        Set<Album> albums = song.getAlbums();
        for (Album a :
                albums) {
            song.removeAlbum(a);
        }


        song.removeGenre(song.getGenre());

        songRepository.deleteById(id);
    }

    @Override
    public Album syncAlbum(Album album) {
        return albumRepository.saveAndFlush(album);
//        return albumRepository.save(album);
    }

    @Override
    public Album findAlbumById(Long id) {
        return albumRepository.findById(id).get();
    }

    @Override
    public List<Album> findAllAlbums() {
        return albumRepository.findAll();
    }

    @Override
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }

    @Override
    public Genre syncGenre(Genre genre) {
        return genreRepository.saveAndFlush(genre);
//        return genreRepository.save(genre);
    }

    @Override
    public Genre findGenreById(Long id) {
        return genreRepository.findById(id).get();
    }

    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}
