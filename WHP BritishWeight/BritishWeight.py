class BritishWeight:
    """
    >>> print(BritishWeight(pound=15))
    1 stone 1 pound
    >>> print(BritishWeight(pound=11))
    11 pound
    >>> a = BritishWeight(stone=20, pound=10)
    >>> b = BritishWeight(pound=10)
    >>> c = BritishWeight(stone=21, pound=6)
    >>> a + b == c
    True
    >>> a + c
    BritishWeight(stone=42, pound=2)
    >>> BritishWeight(pound=15)
    BritishWeight(stone=1, pound=1)
    """
    def __init__(self, stone=0, pound=0):
        self.pound = stone * 14 + pound

    def __str__(self):
        return (f'{self.pound // 14} stone ' if self.pound // 14 != 0 else '') + f'{self.pound % 14} pound'

    def __add__(self, other):
        return BritishWeight(pound=self.pound + other.pound)

    def __eq__(self, other):
        return self.pound == other.pound

    def __repr__(self):
        return f'BritishWeight({f"stone={self.pound // 14}, " if self.pound // 14 != 0 else ""}pound={self.pound % 14})'

