import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)
// Name: Ryan Little
// Student ID: 501183218
public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your library
		AudioContentStore store = new AudioContentStore();
		
		// Create my music library
		Library library = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

			// Process keyboard actions
			while (scanner.hasNextLine()) {
				String action = scanner.nextLine();
				try { // encapsulates entire elif tree in a try statement and catches at the end
					if (action == null || action.equals("")) {
						System.out.print("\n>");
						continue;
					} else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
						return;
					else if (action.equalsIgnoreCase("SEARCH")) {
						// asks user for title
						System.out.print("Title: ");
						String songTitle = "";
						if (scanner.hasNextLine()) {
							songTitle = scanner.nextLine();
						}
						// goto store, search for title using maps
						int storeValue = store.searchTitle(songTitle);
						System.out.print(storeValue + 1 + ". ");
						store.getContent(storeValue + 1).printInfo();
					} else if (action.equalsIgnoreCase("SEARCHA")) {
						// asks user for artist
						System.out.print("Artist: ");
						String artist = "";
						if (scanner.hasNextLine()) {
							artist = scanner.nextLine();
						}
						// initializes arraylist to store result
						ArrayList<Integer> storeValue = new ArrayList<Integer>();
						// goto store, search for artist songs using maps
						storeValue = store.searchArtist(artist);
						// loop through the arraylist and print
						for (int i = 0; i < storeValue.size(); i++) {
							System.out.print(storeValue.get(i) + 1 + ". ");
							store.getContent(storeValue.get(i) + 1).printInfo();
						}
					} else if (action.equalsIgnoreCase("SEARCHG")) {
						// asks user for genre, uses the enum as a list for printing purposes
						System.out.print("Genre " + java.util.Arrays.asList(Song.Genre.values()));
						String genre = "";
						if (scanner.hasNextLine()) {
							genre = scanner.nextLine();
						}
						// initializes arraylist to store result
						ArrayList<Integer> storeValue = new ArrayList<Integer>();
						// goto store, search for genre using maps
						storeValue = store.searchGenre(genre);
						// loop through arraylist and print
						for (int i = 0; i < storeValue.size(); i++) {
							System.out.print(storeValue.get(i) + 1 + ". ");
							store.getContent(storeValue.get(i) + 1).printInfo();
						}
					} else if (action.equalsIgnoreCase("DOWNLOADA")) {
						// asks user for artist name
						System.out.print("Artist Name: ");
						String artist = "";
						if (scanner.hasNextLine()) {
							artist = scanner.nextLine();
						}
						// creates arraylist to hold result
						ArrayList<Integer> artistSongs = new ArrayList<Integer>();
						// uses maps in audiocontentstore method
						artistSongs = store.searchArtist(artist);

						for (int i = 0; i < artistSongs.size(); i++) {
							try { // tries to download every song in arraylist
								AudioContent content = store.getContent(artistSongs.get(i) + 1);
								library.download(content);
							}
							catch (RuntimeException e) // catches all runtime exceptions, prints the error message
							{
								System.out.println(e.getMessage());
							}
						}
					} else if (action.equalsIgnoreCase("DOWNLOADG")) {
						// user input
						System.out.print("Genre: ");
						String genre = "";
						if (scanner.hasNextLine()) {
							genre = scanner.nextLine();
						}
						// arraylist initialization
						ArrayList<Integer> genreSongs = new ArrayList<Integer>();
						// goto store, search for genre using maps
						genreSongs = store.searchGenre(genre);
						for (int i = 0; i < genreSongs.size(); i++) {
							try { // tries to download every content in arraylist
								AudioContent content = store.getContent(genreSongs.get(i) + 1);
								library.download(content);
							}
							catch (RuntimeException e)
							{
								System.out.println(e.getMessage()); // catches and prints error message
							}
						}
					} else if (action.equalsIgnoreCase("STORE"))    // List all songs
					{
						store.listAll();
					} else if (action.equalsIgnoreCase("SONGS"))    // List all songs
					{
						library.listAllSongs();
					} else if (action.equalsIgnoreCase("BOOKS"))    // List all songs
					{
						library.listAllAudioBooks();
					} else if (action.equalsIgnoreCase("PODCASTS"))    // List all songs
					{
						library.listAllPodcasts();
					} else if (action.equalsIgnoreCase("ARTISTS"))    // List all songs
					{
						library.listAllArtists();
					} else if (action.equalsIgnoreCase("PLAYLISTS"))    // List all play lists
					{
						library.listAllPlaylists();
					} else if (action.equalsIgnoreCase("DOWNLOAD")) {
						int startIndex = 0;
						int endIndex = 0;
						// two download indicies
						System.out.print("From Store Content #: ");
						if (scanner.hasNextInt()) {
							startIndex = scanner.nextInt();
							scanner.nextLine(); // consume nl
						}
						System.out.print("To Store Content #: ");
						if (scanner.hasNextInt()) {
							endIndex = scanner.nextInt();
							scanner.nextLine(); // consume nl
						}
							for (int i = startIndex; i <= endIndex; i++) {
								try { // download all content in range
									AudioContent content = store.getContent(i);
									library.download(content);
								}
								catch (RuntimeException e)
								{
									System.out.println(e.getMessage()); // if any error, catch and print error msg
								}
							}


					} else if (action.equalsIgnoreCase("PLAYSONG")) {
						int index = 0;

						System.out.print("Song Number: ");
						if (scanner.hasNextInt()) {
							index = scanner.nextInt();
							// consume the nl character since nextInt() does not
							scanner.nextLine();
						}
						library.playSong(index);
					} else if (action.equalsIgnoreCase("BOOKTOC")) {
						int index = 0;

						System.out.print("Audio Book Number: ");
						if (scanner.hasNextInt()) {
							index = scanner.nextInt();
							scanner.nextLine();
						}
						library.printAudioBookTOC(index);
					} else if (action.equalsIgnoreCase("PLAYBOOK")) {
						int index = 0;

						System.out.print("Audio Book Number: ");
						if (scanner.hasNextInt()) {
							index = scanner.nextInt();
						}
						int chapter = 0;
						System.out.print("Chapter: ");
						if (scanner.hasNextInt()) {
							chapter = scanner.nextInt();
							scanner.nextLine();
						}
						library.playAudioBook(index, chapter);
					} else if (action.equalsIgnoreCase("PODTOC")) {
						int index = 0;
						int season = 0;

						System.out.print("Podcast Number: ");
						if (scanner.hasNextInt()) {
							index = scanner.nextInt();
						}
						System.out.print("Season: ");
						if (scanner.hasNextInt()) {
							season = scanner.nextInt();
							scanner.nextLine();
						}
						library.printPodcastEpisodes(index, season);
					} else if (action.equalsIgnoreCase("PLAYPOD")) {
						int index = 0;

						System.out.print("Podcast Number: ");
						if (scanner.hasNextInt()) {
							index = scanner.nextInt();
							scanner.nextLine();
						}
						int season = 0;
						System.out.print("Season: ");
						if (scanner.hasNextInt()) {
							season = scanner.nextInt();
							scanner.nextLine();
						}
						int episode = 0;
						System.out.print("Episode: ");
						if (scanner.hasNextInt()) {
							episode = scanner.nextInt();
							scanner.nextLine();
						}
						library.playPodcast(index, season, episode);
					} else if (action.equalsIgnoreCase("PLAYALLPL")) {
						String title = "";

						System.out.print("Playlist Title: ");
						if (scanner.hasNextLine()) {
							title = scanner.nextLine();
						}
						library.playPlaylist(title);
					} else if (action.equalsIgnoreCase("PLAYPL")) {
						String title = "";
						int index = 0;

						System.out.print("Playlist Title: ");
						if (scanner.hasNextLine()) {
							title = scanner.nextLine();
						}
						System.out.print("Content Number: ");
						if (scanner.hasNextInt()) {
							index = scanner.nextInt();
							scanner.nextLine();
						}
						library.playPlaylist(title, index);
					}
					// Delete a song from the library and any play lists it belongs to
					else if (action.equalsIgnoreCase("DELSONG")) {
						int songNum = 0;

						System.out.print("Library Song #: ");
						if (scanner.hasNextInt()) {
							songNum = scanner.nextInt();
							scanner.nextLine();
						}

						library.deleteSong(songNum);
					} else if (action.equalsIgnoreCase("MAKEPL")) {
						String title = "";

						System.out.print("Playlist Title: ");
						if (scanner.hasNextLine()) {
							title = scanner.nextLine();
						}
						library.makePlaylist(title);
					} else if (action.equalsIgnoreCase("PRINTPL"))    // print playlist content
					{
						String title = "";

						System.out.print("Playlist Title: ");
						if (scanner.hasNextLine())
							title = scanner.nextLine();

						library.printPlaylist(title);
					}
					// Add content from library (via index) to a playlist
					else if (action.equalsIgnoreCase("ADDTOPL")) {
						int contentIndex = 0;
						String contentType = "";
						String playlist = "";

						System.out.print("Playlist Title: ");
						if (scanner.hasNextLine())
							playlist = scanner.nextLine();

						System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
						if (scanner.hasNextLine())
							contentType = scanner.nextLine();

						System.out.print("Library Content #: ");
						if (scanner.hasNextInt()) {
							contentIndex = scanner.nextInt();
							scanner.nextLine(); // consume nl
						}

						library.addContentToPlaylist(contentType, contentIndex, playlist);
					}
					// Delete content from play list
					else if (action.equalsIgnoreCase("DELFROMPL")) {
						int contentIndex = 0;
						String playlist = "";

						System.out.print("Playlist Title: ");
						if (scanner.hasNextLine())
							playlist = scanner.nextLine();

						System.out.print("Playlist Content #: ");
						if (scanner.hasNextInt()) {
							contentIndex = scanner.nextInt();
							scanner.nextLine(); // consume nl
						}
						library.delContentFromPlaylist(contentIndex, playlist);
					} else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
					{
						library.sortSongsByYear();
					} else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
					{
						library.sortSongsByName();
					} else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
					{
						library.sortSongsByLength();
					}
				}

				catch (RuntimeException e)
				{
					System.out.println(e.getMessage()); // catches any exception that may occur and prints error message
				}
			System.out.print("\n>");
		}
	}
}
