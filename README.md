# Touhoudx
Calculus-Based Shoot-em-up for Code Day

## TO DO
- [ ] GET JAR FILE (using LFS)
- [ ] Enemies (Non-bosses)
- [ ] Optimization (in progress)
- [ ] Drootr boss
- [ ] Some type of level progression
- [ ] Pause menu

## Github Help
### Merge Conflict if you DON'T need code you were working on
  Either discard all changes or run `git fetch upstream` then `git reset --hard origin/master` on git shell
### Merge Conflict if you DO need code you were working on
  In git shell, run `git fetch upstream` then run `git rebase upstream/master`
  When rebase finds any files with merge conflicts on them, it'll tell you the file name(s). 
  Open the files in the IDE, there will be blocks of code that read something like
  ```
  <<<<<<< HEAD
  Stuff that was on the original branch
  ==========
  Stuff that is on your code
  >>>>>>> branch name
  ```
  Find the one that is more recent and delete the other stuff, then when you're done do
  `git add "name of file".java` and
  `git commit` with a commit message. Repeat until there aren't any merge conflicts and code should be good.

## Links
[Music](https://moriyashrine.org/official-soundtracks/)

[Drootr Website](http://drootr.com/)

[Cirno's Perfect Math Class](https://www.youtube.com/watch?v=qrN3EC5_dA8)
