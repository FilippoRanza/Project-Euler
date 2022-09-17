#! /usr/bin/python

import os
import re

def find_current_problem(base_dir):
    content = os.listdir(base_dir)
    problem_dir_regex = re.compile(r"^problem_(\d+)$")
    matches = (problem_dir_regex.match(entry) for entry in content)
    problem_ids = (int(match.group(1)) for match in matches if match is not None)
    return max(problem_ids)

# Comment: very useful 
def make_next_problem_package(base_dir, curr_id):
    problem_id = curr_id + 1
    new_problem_dir = f"problem_{problem_id}"
    new_problem_path = os.path.join(base_dir, new_problem_dir)
    os.mkdir(new_problem_path)
    os.chdir(new_problem_path)
    new_problem_file = f"problem_{problem_id}.scala"
    with open(new_problem_file, "w") as file:
        print(f"package problem_{problem_id}", file=file)
        print(f"", file=file)
        print(f"", file=file)
        print(f"", file=file)
        print(f"def problem_{problem_id}(): Int = {problem_id}", file=file)
        print(f"", file=file)
        print(f"", file=file)
        print(f"", file=file)
    




def main():
    src_path = os.path.join("src", "main", "scala")
    last_problem = find_current_problem(src_path)
    make_next_problem_package(src_path, last_problem)
    print("created package for problem", last_problem + 1)

if __name__ == '__main__':
    main()














