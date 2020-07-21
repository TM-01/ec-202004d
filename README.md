# Repository
- (例) OrderRepository
  - load(int id) : Order //idから注文情報を1件検索

- ItemRepository
  - findByName(String fuzzyName, String sort) : List<Item> //fuzzyNameから商品情報を曖昧検索(なければ全件検索)
  - load(int id) : Item //主キー検索
  - findAllName() : List<String> // 商品名を全件検索する
  - findByName(String name) : List<String> // 指定された商品名を検索します（曖昧不可）
  - findAllName() : List<String> //商品名をすべて検索
  - insert(Item item) : void //商品を登録します
  - findMaxId() : int // 現在登録されているIDの最大値を検索します
  
- ToppingRepository
  - findAll() : List<Topping> //全トッピングを検索
  
- UserRepository
  - findByEmail(String email) : User //emailからユーザ情報を検索(なければnullを返す)
  - load(int id) : User //ユーザIDからユーザ情報を検索します
  - insert(User user) :void //新規ユーザ情報をusersテーブルに挿入
  - updateTotalPrice(int orderId,int totalPrice) : void //オーダの合計金額を更新する
  - delete(int userId) : void //ユーザ情報を削除します
  
- OrderRepository
  - findByUserIdAndStatus(int userId, int status) : List<Order> // ユーザIDで注文情報を取得する
  - insert(int userId) : void // ユーザIDを指定しオーダを作成します
  - insert(User user) : void // ユーザ情報からオーダを作成します
  - updateStatus(int orderId, int status) : void // 注文IDからステータス情報をアップデートする
  - updateDestinationAndPaymentMethod(Order order,int status) : void // オーダの宛先情報と支払方法を更新する

- OrderItemRepository
  - findByOrderIdAndStatus(Integer orderId,Integer status) : List<OrderItem> //注文IDと状態から注文商品を検索(なければ空のリスト)
  - deleteOrderItem(Integer orderItemId, Integer orderId) :void //注文商品をorder_itemsから削除する
  - insertItem(OrderItem orderItem) : int //オーダ表にカートに商品を追加します
  
- OrderToppingRepository
  - deleteOrderTopping(int orderItemId) : void //注文トッピングをorder_toppingsから削除する
  - insertTopping(List<OrderTopping> orderToppingList) : void //トッピングオーダのリストを受け取ってその情報を登録します
  - findByOrderItemId(int orderItemId) : List<OrderTopping> //注文商品IDで注文トッピングの一覧を取得する
  
