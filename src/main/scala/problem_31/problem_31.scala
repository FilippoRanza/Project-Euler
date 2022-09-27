package problem_31

import scala.collection.immutable.HashSet

def problem_31(): Int =
  /*
  val wallet = make_wallet(initial_value)
  val set    = find_all_combinations(wallet, Coin.TwoPound)
  set.filter(keep_valid).size
   */
  brute_force_solution()

def brute_force_solution(): Int =
  var count = 0
  for tpound <- (0 to 1) do
    for opound <- (0 to 2) do
      for fp <- (0 to 4) do
        for twentyp <- (0 to 10) do
          for ten <- (0 to 20) do
            for five <- (0 to 40) do
              for two <- (0 to 100) do
                for one <- (0 to 200) do
                  val list = List(
                    Cash(tpound, Coin.TwoPound),
                    Cash(opound, Coin.OnePound),
                    Cash(fp, Coin.FiftyPence),
                    Cash(twentyp, Coin.TwentyPence),
                    Cash(ten, Coin.TenPence),
                    Cash(five, Coin.FivePence),
                    Cash(two, Coin.TwoPence),
                    Cash(one, Coin.OnePence)
                  )
                  if keep_valid(list) then count += 1

  count

def keep_valid(wallet: List[Cash]): Boolean =
  wallet.map(_.get_value_in_pence()).sum == 200

def make_wallet(f: (Coin) => Int): List[Cash] =
  Coin.values.map(c => Cash(f(c), c)).toList

def final_value(c: Coin): Int =
  if c == Coin.OnePence then 200
  else 0

def initial_value(c: Coin): Int =
  if c == Coin.TwoPound then 1
  else 0

enum Coin:
  case TwoPound, OnePound, FiftyPence, TwentyPence, TenPence, FivePence, TwoPence, OnePence

class Cash(val count: Int, val coin: Coin):
  def get_value_in_pence(): Int =
    val cv = coin_value(coin)
    cv * count
  def has_value: Boolean = count != 0

  def get_coin_count(): Int = count

  def get_coin_type(): Coin = coin

  override def toString(): String =
    s"${count} ${coin}"

  override def equals(other: Any): Boolean =
    other match
      case o: Cash => (o.coin == coin) && (o.count == count)
      case _       => false

  override def hashCode: Int =
    31 * (count + 31 * (coin.ordinal))

def find_all_combinations(wallet: List[Cash], curr: Coin): HashSet[List[Cash]] =
  coins_after(curr)
    .map(c => (c, convert_wallet(wallet, curr, c)))
    .map(rec_find_all)
    .foldLeft(HashSet(wallet))((acc, curr) => acc ++ curr)

def rec_find_all(curr: Coin, wallets: List[List[Cash]]): HashSet[List[Cash]] =
  wallets.map(w => find_all_combinations(w, curr)).foldLeft(HashSet())((acc, curr) => acc ++ curr)

def coins_after(curr: Coin): List[Coin] =
  val idx   = curr.ordinal + 1
  val coins = Coin.values
  val last  = coins.length
  coins.slice(idx, last).toList

def convert_wallet(wallet: List[Cash], from: Coin, to: Coin): List[List[Cash]] =
  val from_idx  = from.ordinal
  val to_idx    = to.ordinal
  val from_cash = wallet(from_idx)
  val all_convs = all_convertions(from_cash, to)
  all_convs.map((f, t) => remap_list(f, from_idx, t, to_idx, wallet))

def remap_list[T](a: T, idx_a: Int, b: T, idx_b: Int, list: List[T]): List[T] =
  list.updated(idx_a, a).updated(idx_b, b)

def all_convertions(cash: Cash, target: Coin): List[(Cash, Cash)] =
  val count = cash.get_coin_count()
  val from  = cash.get_coin_type()
  (1 to count)
    .filter(n => can_convert(n, from, target))
    .map(n => convert_coins(n, from, target))
    .map((cf, ct) => (Cash(count - cf, from), Cash(ct, target)))
    .toList

def can_convert(count: Int, from: Coin, to: Coin): Boolean =
  val from_val = coin_value(from)
  val to_val   = coin_value(to)
  (count * from_val) % to_val == 0

def convert_coins(orig: Int, from: Coin, to: Coin): (Int, Int) =
  val from_val    = coin_value(from)
  val to_val      = coin_value(to)
  val total_value = (orig * from_val)
  val next        = total_value / to_val
  (orig, next)

def convert_cash(cash: Cash, target: Coin): Option[Cash] =
  val curr      = cash.get_value_in_pence()
  val target_cv = coin_value(target)
  if curr % target_cv == 0 then
    val next = curr / target_cv
    Some(Cash(next, target))
  else None

def coin_value(coin: Coin): Int =
  coin match
    case Coin.TwoPound    => 200
    case Coin.OnePound    => 100
    case Coin.FiftyPence  => 50
    case Coin.TwentyPence => 20
    case Coin.TenPence    => 10
    case Coin.FivePence   => 5
    case Coin.TwoPence    => 2
    case Coin.OnePence    => 1
