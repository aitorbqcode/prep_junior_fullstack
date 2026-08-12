import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SkillsMaster {

    public static int countSkills(int[] tree, Set<Integer> required) {
        if(required == null){
            return 0;
        }

        Iterator<Integer> values = required.iterator();
        Set<Integer> skills = new HashSet<Integer>();
        int num = 0;

        while (values.hasNext()){
            num = values.next();
            skills.add(num);
            num = tree[num];
            skills.add(num);
            while(num != tree[num] || num != 0){
                skills.add((tree[num]));
                num = tree[num];
            }
        }

        return skills.size();
    }


    public static void main(String[] args) {
        int[] tree = { 0, 0, 0, 1, 3, 3, 2 };
        System.out.println(countSkills(tree, Set.of(4,5)));
    }
}

/*
Below is described a skill tree:
Tree

The array that describes this skill tree is as follows:

[
 0, # 0 is the root and does not depend on any skill.
 0, # 1 is unlocked by skill 0 (skill at index 0).
 0, # 2 is unlocked by skill 0.
 1, # 3 is unlocked by skill 1.
 3, # 4 is unlocked by skill 3.
 3, # 5 is unlocked by skill 3.

 2  # 6 is unlocked by skill 2.
]

In another words, each skill is identified by its index in the array, and its value identifies the skill that unlocks it.
Your Task:

Given a skill tree described as an array and a set of required skills, return the total number of skills needed to learn all of the required skills.

Intuition: In the example's tree, if I want to learn skill 6, I first need to learn skills 0 and 2 - a total of 3 skills learned.controller
 */
