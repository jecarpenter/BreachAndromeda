The Problem Solving Framework : 'UPER'

    U = "Understand"
    P = "Plan"
    E = "Execute"
    R = "Reflect" / "Refactor"

   1. Understanding the Problem
   
The problem is I don't know anything about game development.  I'm not sure what library to choose or how to use it.
   
I'm having issues getting the background to render properly on the screen.

My ship object is not colliding with boundaries properly.  Sometimes phasing partially through them, and sometimes hitting invisible walls.

Game keeps crashing when I try to remove ship object from the ship list.

There's an issue on the main menu screen where the buttons aren't displaying properly.  They're supposed to be more 
of a pill shape, but at the moment two of the three are square, and deformed.  It's definitely a visual glitch.

I woke up this morning and all of my library imports are broken.  I'm getting an error that says "Cannot resolve symbol badlogic". 

   2. Planning the Solution
   
I will research different development libraries and learn about the differences in complexity to determine the 
   best library to use.
   
I'm going to watch some tutorials covering how to properly set backgrounds to the screen in libGDX.

I'll be looking up a video on how other people have set screen boundaries for their character models in the past, 
and implement some different fixes to find a solution.

Going to go back to square 1 with the ship iterators and make sure I haven't made any game breaking mistakes.  I will attempt to refactor my code to be more readable at the same time.

I'll be doing some research into the scene2d library that's included with libGDX to determine if I made any coding mistakes that are causing the issue.

I posted in the slack channel to see if anyone might have any ideas.  I'll be doing some more googling to see if there's a way to avoid having to recreate the project.
    
   3. Executing the Plan 
   
After doing research, I have determined LibGDX to be the best library in theory, and I will begin development 
   with this new library.
   
I've watched a few tutorials and feel confident in setting and rendering backgrounds for any of my game screens.

I found a good article covering bounding boxes and centering them in libGDX.  I refactored some of my code to 
include bounding boxes to handle hit boxes, it's working much better now.

I actually rewrote the laser detection method in the game screen class, and now it's more robust.  

During execution, I found the visual bug only occurs when I add a height value to my table row.  I believe what was happeneing was that I was making the row height smaller than the minimum height of the row class, so it was overlapping my buttons causing the bug.  I ended up throwing out the table code, and appending the buttons to the screen directly and hard coding the positions.

After doing some googling and talking with Craig, it seems the only thing I can really do is use the libGDX engine to create a new project, and migrate all the code over, since even rolling back to a prior commit isn't fixing the issue.

    
   4. Reflection / Refactor
   
LibGDX has been a great tool, and if I had to go back and choose a different library I don't think I would choose a different one.
   
Not quite sure what I can really reflect on for this one.  I'm happy with the outcome of executing my plan.

Happy with the bounding box change!  I think I will be implementing it with other hit boxes in the future.

The laser detection method I rewrote could be improved. It doesn't follow single responsibility as closely as I'd like, but I plan to do some more refactoring down the line if I have time.

In the end I probably shouldn't have included a table and rows for only a couple of buttons.

Looking back I should have done more commits, since I did lose a bit of my progress because I had some code that wasn't committed. 
   
    