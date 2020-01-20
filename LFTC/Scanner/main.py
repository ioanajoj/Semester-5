from Scanner import Scanner

print("Hello World!")

codification_table_file = "inputs\codification_table.in"
program_file = "inputs\input.ig"
scanner = Scanner(codification_table_file, program_file)

print("Read codification table: " + str(scanner.codification_table))

print("Scanning input file...")
scanner.scan()

print("=============================")
print("|\tProgram internal form\t|")
print("=============================")
for pair in scanner.pif:
    print("|\t\t" + str(pair[0]) + "\t\t|\t\t" + str(pair[1]) + "\t|")
    print("-----------------------------")

print("\n\n", end="")

print("=============================")
print("|\t\tSymbol Table\t\t|")
print("=============================")
print(scanner.st)

if len(scanner.errors):
    print("Found errors:")
    for error in scanner.errors:
        print(error)


print("\nBye World!")
