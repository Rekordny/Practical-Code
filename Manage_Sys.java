/**
 * @author YufeiChen
 * This is a javaFX-base program similar to a calculator which allows you to record China-Xuzhou-Juanyanchang engineer 
 information and do some simple calculation 
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceBoxBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application{
	//花销项目
	enum  toBuy {
        BANGONG(1,"office supplies",0),JIAJU(2,"office furniture",0),ZHAODAI(3,"entertainment fee",0),CAIGOU(4,"raw material budget",0),SHEBEI(5,"machine purchase",0),WEIHU(6,"machine maintain",0);

        int id;
        String name;
        double total;
        toBuy() {

        }
        toBuy(int id,String name,double total){
            this.id = id;
            this.name = name;
            this.total = total;
        }
        @Override
        public String toString() {
            return this.name;
        }
        
	}
	//修改预算金额方法
	public static void changeYS(double amount,toBuy toBuy) {
    	toBuy.total = toBuy.total - amount;
    	Refresh();
    }
	
	public static void changeDefault(int id, double amount) {
		switch(id){
			case 1: toBuy.BANGONG.total = amount;
			case 2: toBuy.JIAJU.total = amount;
			case 3: toBuy.ZHAODAI.total = amount;
			case 4: toBuy.CAIGOU.total = amount;
			case 5: toBuy.SHEBEI.total = amount;
			case 6: toBuy.WEIHU.total = amount;
		}
	}
	
	//检测String是否为全数字
	public static boolean isNumric(String str) {
		char[] arrary = str.toCharArray();
		boolean result = true;
		for(char a:arrary) {
			if (!Character.isDigit(a)) {
				result = false;
			}
		}
		return result;
	}
	
	static String s1 = "office supplies budget: ";
	static String s2 = "office furniture budget: ";
	static String s3 = "entertainment fee budget: ";
	static String s4 = "raw material budget: ";
	static String s5 = "machine purchase budget: ";
	static String s6 = "machine maintain budget: ";
	
	Button DONE;
	
	Label BGYP = new Label(s1);
	TextField BGYPF = new TextField();
	Label BGJJ = new Label(s2);
	TextField BGJJF = new TextField();
	Label ZDF = new Label(s3);
	TextField ZDFF = new TextField();
	Label CGYL = new Label(s4);
	TextField CGYLF = new TextField();
	Label CGSB = new Label(s5);
	TextField CGSBF = new TextField();
	Label SBWH = new Label(s6);
	TextField SBWHF = new TextField();
	Label DONEYS = new Label("Click DONE to save configure");
	
	static Label BGYPL = new Label(s1 + toBuy.BANGONG.total);
	static Label BGJJL = new Label(s2 + toBuy.JIAJU.total);
	static Label ZDFL = new Label(s3 + toBuy.ZHAODAI.total);
	static Label CGYLL = new Label(s4 + toBuy.CAIGOU.total);
	static Label CGSBL = new Label(s5 + toBuy.SHEBEI.total);
	static Label SBWHL = new Label(s6 + toBuy.WEIHU.total);
	public static void Refresh() {
		BGYPL.setText(s1 + toBuy.BANGONG.total);
	//	System.out.println(BGYPL);
		BGJJL.setText(s2 + toBuy.JIAJU.total);
	//	System.out.println(BGJJL);
		ZDFL.setText(s3 + toBuy.ZHAODAI.total);
		CGYLL.setText(s4 + toBuy.CAIGOU.total);
		CGSBL.setText(s5 + toBuy.SHEBEI.total);
		SBWHL.setText(s6 + toBuy.WEIHU.total);
	}
	
	private Label enterAmount;
	private TextField amount;
	private Label enterName;
	private TextField Name;
	private Label enterTime;
	private TextField time;
	private Label enterTip;
	private TextField Tip;
	private ChoiceBox choiceBoxRef;
	private VBox box;
	private Text firstLine;
	private static int tempID;
	private Button add;
	private Button check;
	private Button DEFAULT;
	
	private Button Refresh;
	
	static class Record{
		double amount;
		String name;
		String time;
		String tip;
		Button delete = new Button("DELETE");
		public Record(double amount, String name, String time, String tip) {
			this.name = name;
			this.amount = amount;
			this.time = time;
			this.tip = tip;
		}
	}
	//设置监听
	ObservableList<Record> listData = FXCollections.observableArrayList();
	ObservableList cursors = FXCollections.observableArrayList(
			toBuy.CAIGOU,
			toBuy.WEIHU,
			toBuy.SHEBEI,
            toBuy.BANGONG,
            toBuy.JIAJU,
            toBuy.ZHAODAI
            
    );
	
	//设置单元格显示
	static class vorListCell extends ListCell<Record>{
		protected void updateItem(Record arg0, boolean arg1) {
            super.updateItem(arg0, arg1);
	
			if(arg0 == null) {
	            this.setText("");
	        }else {
	            this.setText("People in charge: " + arg0.name + " \tAmount: " + arg0.amount + " \tTime: " + arg0.time + " \tComment: " + arg0.tip + arg0.delete);
	        }
		}
	}
	@Override
	public void start(Stage PrimaryStage) {
		try {
			Refresh();
			PrimaryStage.setTitle("Manage_System@YufeiChen");
			enterAmount = new Label("Amount to change: ");
			amount = new TextField();
			amount.setPrefWidth(100);
			amount.setOnAction(this::events);

			enterName = new Label("People in charge: ");
			Name = new TextField();
			Name.setPrefWidth(100);
			Name.setOnAction(this::events);
			
			enterTime = new Label("Time to change: ");
			time = new TextField();
			time.setPrefWidth(160);
			time.setOnAction(this::events);
			
			
			choiceBoxRef = ChoiceBoxBuilder.create().items(cursors).build();
			box = new VBox();
			box.getChildren().add(choiceBoxRef);
			choiceBoxRef.setOnAction(event -> {
                toBuy value = (toBuy) choiceBoxRef.getValue();
                tempID = value.id;
            });
			
			
			enterTip = new Label("Comment:");
			Tip = new TextField();
			Tip.setPrefWidth(500);
			Tip.setOnAction(this::events);
			
			firstLine = new Text("Click SAVE to save record");
			
			
			DEFAULT = new Button("Set up budgets");
			DEFAULT.setOnAction(this::events);
			add = new Button("SAVE");
			add.setOnAction(this::events);
			check = new Button("Check records");
			check.setOnAction(this::events);
			DONE = new Button("DOWN(Default 0)");
			DONE.setOnAction(this::events);
			Refresh = new Button("Manual refresh(spare usage)");
			Refresh.setOnAction(this::events);
			FlowPane pane = new FlowPane(DEFAULT,enterAmount, amount, enterName, Name, enterTime, time, enterTip, box, add, check, firstLine,
					BGYPL,BGJJL, ZDFL,CGYLL,CGSBL,SBWHL,Refresh);
			pane.setHgap(20);
			pane.setVgap(10);
			pane.setAlignment(Pos.CENTER);
			pane.setOrientation(Orientation.VERTICAL);
			
			
			Scene scene = new Scene(pane, 600, 700);
			PrimaryStage.setScene(scene);
			PrimaryStage.show();
		}catch (Exception e) {
			System.out.println("Unkown error");
			Stage error = new Stage();
			Label mistake = new Label("Unkown error");
			FlowPane pane = new FlowPane(mistake);
			pane.setAlignment(Pos.CENTER);
			Scene errorScene = new Scene(pane,100,100);
			error.setScene(errorScene);
			error.show();
		}
	}
	//点击事件
	public void events(ActionEvent event){
		//刷新主页面
		if (event.getSource() == Refresh) {
			Refresh();
		}
		
		//点击设置预算
		if (event.getSource() == DEFAULT) {
			FlowPane root = new FlowPane(BGYP,BGYPF,BGJJ,BGJJF,ZDF,ZDFF,CGYL,CGYLF,CGSB,CGSBF,SBWH,SBWHF,DONE,DONEYS);
			Stage third = new Stage();
			third.setTitle("Budget configure");
			root.setOrientation(Orientation.VERTICAL);
			Scene scene3 = new Scene(root, 500, 600);
			third.setScene(scene3);
			third.show();
			
		}
		
		//在预算页面设置完成
		if (event.getSource() == DONE) {
			if ((!isNumric(BGYPF.getText()))||(!isNumric(BGJJF.getText()))||(!isNumric(ZDFF.getText()))||(!isNumric(CGYLF.getText()))||(!isNumric(CGSBF.getText()))||(!isNumric(SBWHF.getText()))) {
				DONEYS.setText("Enter number please");
			}else {
				if (BGYPF.getText().isEmpty()) {
					BGYPF.setText("0");
				}
				if (BGJJF.getText().isEmpty()) {
					BGJJF.setText("0");
				}
				if (ZDFF.getText().isEmpty()) {
					ZDFF.setText("0");
				}
				if (CGYLF.getText().isEmpty()) {
					CGYLF.setText("0");
				}
				if (CGSBF.getText().isEmpty()) {
					CGSBF.setText("0");
				}
				if (SBWHF.getText().isEmpty()) {
					SBWHF.setText("0");
				}
				changeDefault(1, Double.parseDouble(BGYPF.getText()));
				changeDefault(2, Double.parseDouble(BGJJF.getText()));
				changeDefault(3, Double.parseDouble(ZDFF.getText()));
				changeDefault(4, Double.parseDouble(CGYLF.getText()));
				changeDefault(5, Double.parseDouble(CGSBF.getText()));
				changeDefault(6, Double.parseDouble(SBWHF.getText()));
				Refresh();
				DONEYS.setText("Budget saved.");
			}
			
			
			
			
		}
		
		
		if (event.getSource()==add) {
			String tipIn = "";
			switch(tempID) {
				case 1:tipIn = "office supplies";
				case 2:tipIn = "office furniture";
				case 3:tipIn = "entertainment fee";
				case 4:tipIn = "raw material";
				case 5:tipIn = "machine purchase";
				case 6:tipIn = "machine maintain";
			}
			if ((amount.getText().isEmpty())||(Name.getText().isEmpty()||(time.getText().isEmpty())||(tipIn == ""))) {
				firstLine.setText("must give time, people in charge and amount");
			}else if (!isNumric(amount.getText())) {
				firstLine.setText("Amount must be a number");
			}else {
				double DoubleIn = Double.parseDouble(amount.getText());
				String nameIn = Name.getText();
				String timeIn = time.getText();
				
				
				Record newRecord = new Record(DoubleIn, nameIn, timeIn, tipIn);
				listData.add(newRecord);
				firstLine.setText("Record saved.");
				Name.setText("");
				time.setText("");
				amount.setText("");
				System.out.println(tempID);
				if (tempID == 1) {
					changeYS(DoubleIn,toBuy.BANGONG);
				}else if (tempID == 2) {
					changeYS(DoubleIn,toBuy.JIAJU);
				}else if (tempID == 3) {
					changeYS(DoubleIn,toBuy.ZHAODAI);
				}else if (tempID == 4) {
					changeYS(DoubleIn,toBuy.CAIGOU);
				}else if (tempID == 5) {
					changeYS(DoubleIn,toBuy.SHEBEI);
				}else {
					changeYS(DoubleIn,toBuy.WEIHU);
				}
			}
			
		}
		
		if (event.getSource() == check) {
			BorderPane root = new BorderPane();
			//创建ListView
			ListView<Record> listView = new ListView<Record>();
			//设置数据源
			listView.setItems(listData);
			//单元格生成器
			listView.setCellFactory(new Callback<ListView<Record>, ListCell<Record>>() {
				
				@Override
				public ListCell<Record> call(ListView<Record> param) {
					return new vorListCell();
				}
			});
			//添加到布局
			root.setCenter(listView);
			
			Stage second = new Stage();
			second.setTitle("Records");
			Scene scene2 = new Scene(root, 500, 600);
			second.setScene(scene2);
			second.show();
		}
			
	}
	public static void main(String args) {
		launch(args);
	}
}
