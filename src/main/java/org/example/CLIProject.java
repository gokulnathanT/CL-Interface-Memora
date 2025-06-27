package org.example;

import picocli.CommandLine;

@CommandLine.Command(
        name = "CLI",
        description = "The CLI Project",
        subcommands = {
                memoraCommand.class
        }
)
public class CLIProject implements Runnable{
    public static void main(String[] args) {
        CommandLine commandLine =new CommandLine(new CLIProject());
        commandLine.registerConverter(ConfigElement.class,ConfigElement::from);
        commandLine.parseWithHandler(new CommandLine.RunLast(),args);

    }

    @Override
    public void run() {
        System.out.println("Memora CLI - Personal Assistant");
    }
}
