package symbol_iterator

trait SymbolIterator[T]:
  def size(): Int
  def iter(): LazyList[T]

def int_log10(n: Int, acc: Int): Int =
  n match
    case 0 => acc
    case _ => int_log10(n / 10, acc + 1)

def int_symbols(n: Int): LazyList[Int] =
  n match
    case 0 => LazyList.empty
    case _ => (n % 10) #:: int_symbols(n / 10)

class IntSymbols(n: Int) extends SymbolIterator[Int]:
  private var value                  = n
  override def size(): Int           = int_log10(value, 0)
  override def iter(): LazyList[Int] = int_symbols(value)
