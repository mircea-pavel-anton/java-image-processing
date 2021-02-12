# Simp

A Simple Image Manipulation Program (SIMP) written in Java as a university assignment.

## How to use

To run the `.jar` file available in the releases page, you run the following command: `java -jar Simp.jar`.

To see the `help` screen, use the `-h` or `--help` flags.

!["Help command"](./res/readme/simp_help.png)

To specify an input file, use the `-i` or `--input` flag. To specify an output file, use the `-o` or `--output` flag.

Only after specifyin the `input` and `output` flags, in either long or short form, the program will start parsing the input file.  
If the file already exists, the program will ask if you wish to overwrite it.

!["Usage showcase"](./res/readme/simp_usage.gif)

You can check out the [before](./res/input/doggo.bmp) and [after](./res/output/doggo_out.bmp) images.

## What I Learned

1. How to implement the Producer-Consumer design pattern
2. Some basic digital image processing algorithms
3. How to write a custom parser for various file formats (24-bit BMP in this case)

Overall, i found this project rather captivating, as I have never touched digital image processing before. While I know these are trivial algorithms, I enjoyed implementing them and I am happy ith the result.

Writing my own parser for the 24-bit BMP file format was... interesting, and a bit more challenging than I expected it to be.

I have never attempted to implement the `Producer-Consumer` design pattern before, so implementing it now was prime learning material. Ideally, I would have implemented my own custom `BlockingQueue`, but i ran out of time and energy.

## Feedback, suggestions and help

For feedback, suggestions, bug reports etc., feel free to e-mail me at 'mike.anth99@gmail.com'.

---

_a project by Mircea-Pavel Anton (Mike Anthony)_
