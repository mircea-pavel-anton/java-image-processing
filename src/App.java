import objects.singletons.ArgParser;

public class App {

	public static void main(String[] args) {
		ArgParser argParser = ArgParser.getInstance();
		try {
			argParser.parseArguments(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		System.out.println("Args:");
		System.out.println(argParser.getInputFilePath());
		System.out.println(argParser.getOutputFilePath());
		System.out.println(argParser.getFilterType());
	}
}