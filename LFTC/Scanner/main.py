from Scanner import Scanner

print("Hello World!")

codification_table_file = "codification_table.in"
program_file = "input.ig"
scanner = Scanner(codification_table_file, program_file)

print("Codification table: " + str(scanner.codification_table))

print("Calling scanner")
scanner.scan()

print(scanner.pif)
print(scanner.st)
print(scanner.errors)

print("Bye World!")
