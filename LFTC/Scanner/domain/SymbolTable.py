
class SymbolTable:
    def __init__(self):
        self.counter = 0
        self.root = None

    def search(self, symbol):
        """
        Get ST_pos of a given symbol
        :param symbol: Valid identifier
        :return: ST_pos in current Symbol Table, or -1 if not found
        """
        if not self.root:
            return -1
        return self.search_node(symbol, self.root)

    def search_node(self, symbol, node):
        """
        Helper function for search; does a binary search in a tree
        :param symbol: Valid identifier
        :param node: Node type argument
        :return: position in Symbol Table or -1 if not found
        """
        if not node:
            return -1
        if node.symbol == symbol:
            return node.ST_pos
        if node.symbol < symbol:
            return self.search_node(symbol, node.left)
        if node.symbol > symbol:
            return self.search_node(symbol, node.right)

    def add_symbol(self, symbol):
        """
        Add a new symbol to the Symbol Table if not found already
        :param symbol: Valid identifier
        :return: ST_pos of the symbol
        """
        search_result = self.search(symbol)
        if search_result != -1:
            return search_result
        new_node = Node(self.counter, symbol)
        if not self.root:
            self.root = new_node
        else:
            current = self.root
            precedent, direction = None, None
            while current is not None:
                precedent = current
                if symbol < precedent.symbol:
                    current = precedent.left
                    direction = "left"
                elif symbol > precedent.symbol:
                    current = precedent.right
                    direction = "right"
            if direction == "left":
                precedent.left = new_node
            else:
                precedent.right = new_node
        self.counter += 1
        return new_node.ST_pos

    def __str__(self):
        msg = ""
        children = [self.root]
        while children:
            child = children.pop(0)
            msg += str(child)
            msg += "\n"
            if child.left:
                children.append(child.left)
            if child.right:
                children.append(child.right)
        return msg


class Node:
    def __init__(self, st_pos, symbol, left=None, right=None):
        self.ST_pos = st_pos
        self.symbol = symbol
        self.left = left
        self.right = right

    def __str__(self):
        return str(self.ST_pos) + " - " + str(self.symbol)
