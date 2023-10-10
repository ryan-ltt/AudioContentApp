import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library
// Name: Ryan Little
// Student ID: 501183218
public class AudioContentStore
{
		private ArrayList<AudioContent> contents;
		private Map<String, Integer> titleMap;
		private Map<String,ArrayList<Integer>> artistMap;
		private Map<String, ArrayList<Integer>> genreMap;
		public AudioContentStore()
		{
			// tries to readfile, if it cant exits the program
			contents = new ArrayList<AudioContent>();
			try
			{
				contents = readFile();
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				System.exit(1);
			}
			// initialize all maps, use hashmap
			titleMap = new HashMap<String, Integer>();
			artistMap = new HashMap<String, ArrayList<Integer>>();
			genreMap = new HashMap<String, ArrayList<Integer>>();
			for (int i = 0; i < contents.size(); i++)
			{
				titleMap.put(contents.get(i).getTitle(),i); // puts every song title into the titleMap
				if (contents.get(i).getType().equals("SONG")) // checks for song type and that it doesnt exist in the map already
				{
					Song outerSong = (Song) contents.get(i); // casts audiocontent in contents to a song
					for (Song.Genre genre : Song.Genre.values()) // for every genre in the enum
					{
						ArrayList<Integer> genreSongs = new ArrayList<Integer>(); // initialize a new arraylist
						for (int j = 0; j < contents.size(); j++) // go through the contents
						{
							if (contents.get(j).getType().equals("SONG")) // once again make sure it is a song
							{
								Song currentSong = (Song) contents.get(j); // cast That song to a song
								if (currentSong.getGenre().equals(genre)) // checks if the genres are the same in the inner song
								{
									genreSongs.add(j); // adds that genre to the arraylist
								}
							}
						}
						genreMap.put(genre.toString(),genreSongs); // once the inner loop is done, maps the genre to that arraylist
					}
					if (!artistMap.containsKey(outerSong.getArtist())) // if the song isnt in the artistmap
					{
						String currentArtist = outerSong.getArtist(); // sets a current artist string
						ArrayList<Integer> artistSongs = new ArrayList<>(); // initializes arraylist
						for (int j = i; j < contents.size(); j++) // loops through the rest of the array to test for same artist, if so, add it to the arraylist
						{
							if (contents.get(j).getType().equals("SONG")) // checks if song
							{
								Song currentSong = (Song) contents.get(j); // casts to song obj
								if (currentSong.getArtist().equals(currentArtist)) // if artists are the same
								{
									artistSongs.add(j); // add it to the temp arraylist
								}
							}

						}
						artistMap.put(outerSong.getArtist(), artistSongs); // add the artist plus the indicies to the artistmap
					}
				}
				if (contents.get(i).getType().equals("AUDIOBOOK"))  // checks for audiobook type and that it doesnt exist in the map already
				{
					AudioBook outerAudioBook = (AudioBook) contents.get(i); // casts to audiobook
					if (!artistMap.containsKey(outerAudioBook.getAuthor())) // if audiobook isnt in artistmap
					{
						String currentAuthor = outerAudioBook.getAuthor(); // sets a current author string
						ArrayList<Integer> authorBooks = new ArrayList<>();
						for (int j = i; j < contents.size(); j++) // loops through the rest of the array to test for same author, if so, add it to the arraylist
						{
							AudioBook currentAudioBook = (AudioBook) contents.get(j); //  casts obj to audiobook
							if (currentAudioBook.getAuthor().equals(currentAuthor)) // if authors are the same
							{
								authorBooks.add(j); // add it to arraylist
							}
						}
						artistMap.put(outerAudioBook.getAuthor(), authorBooks); // add the author plus the indicies to the artistmap
					}

				}

			}
		}
		private static ArrayList<AudioContent> readFile() throws FileNotFoundException
				/*
				read file method!!!
				 */


		{
			// creates a new input file for the store.txt text file
			File inputFile = new File("store.txt");
			// creates a scaner to read the file
			Scanner in = new Scanner(inputFile);

			// creates arraylist of audiocontent for the store
			ArrayList<AudioContent> store = new ArrayList<AudioContent>();

			// loops through every line
			while (in.hasNextLine())
			{
				// creates variable for current line
				String currentLine = in.nextLine();
				// checks for song
				if (currentLine.equals("SONG"))
				{
					// sets all variables necessary for song, in correct order
					String id = in.nextLine();
					String title = in.nextLine();
					String year = in.nextLine();
					String length = in.nextLine();
					String artist = in.nextLine();
					String composer = in.nextLine();
					String genre = in.nextLine();
					String numLines = in.nextLine();
					int numOfLines = Integer.parseInt(numLines); // parses the String to an integer to construct the song
					String file = ""; // sets an empty lyric file
					for (int i = 0; i < numOfLines; i++)
					{
						file = file + in.nextLine(); // adds to the lyrics file
					}
					Song song = new Song(title, Integer.parseInt(year), id, Song.TYPENAME, file, Integer.parseInt(length), artist, composer, Song.Genre.valueOf(genre), file); // constructs the song
					store.add(song); // adds it to the store
					System.out.println("Loading SONG"); // user feedback
				}
				if (currentLine.equals("AUDIOBOOK")) // if its an audibook
				{
					// initialize arraylists necessary for holding chapters and chaptertitles
					ArrayList<String> chapters = new ArrayList<String>();
					ArrayList<String> chapterTitles = new ArrayList<String>();
					// creates necessary variables
					String id = in.nextLine();
					String title = in.nextLine();
					String year = in.nextLine();
					String length = in.nextLine();
					String author = in.nextLine();
					String narrator = in.nextLine();
					String numChapters = in.nextLine();
					int numOfChapters = Integer.parseInt(numChapters); // converts num of chapters to an int
					for (int i = 0; i < numOfChapters; i++) // loops through that many lines and reads chaptertitles
					{
						String strchapterTitles = "";
						strchapterTitles = in.nextLine();
						chapterTitles.add(strchapterTitles);
					}
					for (int i = 0; i < numOfChapters; i++)
					{
						String numLines = in.nextLine();
						int numOfLines = Integer.parseInt(numLines);
						String chapterContent = "";
						for (int j = 0; j < numOfLines; j++)
						{
							chapterContent = chapterContent + in.nextLine(); // for each chapter, take in all the lyrics
						}
						chapters.add(chapterContent); // add it to arraylist
					}
					AudioBook audiobook = new AudioBook(title, Integer.parseInt(year), id, AudioBook.TYPENAME, "", Integer.parseInt(length), author, narrator, chapterTitles, chapters); // construct audiobook
					store.add(audiobook); // add it to store
					System.out.println("Loading AUDIOBOOK"); // user feedback
				}

			}
			return store;
		}
		
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				throw new NotInMap("Content");
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print(index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		// looks through the titlemap for the title and returns it
		public int searchTitle(String title)
		{
			for (String key : titleMap.keySet())
			{
				if (key.equals(title))
				{
					return titleMap.get(key);
				}
			}
			throw new NotInMap(title); // throws exception if not in map
		}
		// looks through the artistmap for the artist and returns it
		public ArrayList<Integer> searchArtist(String artist)
		{
			for (String key : artistMap.keySet())
			{
				if (key.equals(artist))
				{
					return artistMap.get(key);
				}
			}
			throw new NotInMap(artist); // throws exception if not in map
		}
		// looks through the genremap for the genre and returns it
		public ArrayList<Integer> searchGenre(String genre)
		{
			for (String key : genreMap.keySet())
			{
				if (key.equals(genre))
				{
					return genreMap.get(key);
				}
			}
			throw new NotInMap(genre); // throws exception if not in map
		}
		// creates exception class for contentstore if the content is not in the map
	public class NotInMap extends RuntimeException
	{
		public NotInMap(String type){super("No matches for " + type);}
	}
}
