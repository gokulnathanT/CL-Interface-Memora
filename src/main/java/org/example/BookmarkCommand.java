package org.example;

import picocli.CommandLine;

@CommandLine.Command(name="bookmark",description = "-Save helpful links and websites")
public class BookmarkCommand implements Runnable{

    @CommandLine.Option(names={"--info","--I"})
    private boolean info;

    @Override
    public void run() {
        System.out.println("Bookmark for storing URL");
        if(info){
            System.out.println("Save useful links without cluttering your browser.\n" +
                    "- Add bookmarks with title, URL, tags\n" +
                    "- Organize links by purpose or topic\n" +
                    "- Instant access to your favorite resources");
        }
    }
}
