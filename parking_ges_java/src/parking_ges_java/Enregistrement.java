package parking_ges_java;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



public class Enregistrement extends JFrame {
	Statement st;
	Conneccion con=new Conneccion();
	ResultSet rst;
	JTable table;
	JScrollPane scroll;
	JLabel lbtitre1,lbtitre2,lbnum_place,lbtype_place;
	JTextField tfnum_place;
	JComboBox combo_typlace,combo_typlace2;
	JButton btenrg,btsupp,btplace_dispo,btactu;
	
	    public Enregistrement(){
	    	this.setTitle("Parking");
			this.setSize(900,500);
			this.setLocationRelativeTo(null);
			this.setResizable(false);//this.setLocation(230, 80);
			JPanel pn=new JPanel();
			pn.setLayout(null);
			add(pn);
			pn.setBackground(new Color(180,200,240));
			
			
			lbtitre1=new JLabel("Formulaire d'enregistrement des places");
			lbtitre1.setBounds(50,10,700,30);
			lbtitre1.setFont(new Font("Arial",Font.BOLD,22));
			pn.add(lbtitre1);
			
			lbnum_place=new JLabel("Numero place");
			lbnum_place.setBounds(63,50,170,25);
			pn.add(lbnum_place);
			tfnum_place=new JTextField();
			tfnum_place.setBounds(193,50,80,25);
			pn.add(tfnum_place);
			
			
			lbtype_place=new JLabel("Type place");
			lbtype_place.setBounds(63,80,170,25);
			pn.add(lbtype_place);
			combo_typlace=new JComboBox();
			combo_typlace.addItem("");
			combo_typlace.addItem("PLACE VOITURE");
			combo_typlace.addItem("PLACE MOTO");
			combo_typlace.addItem("PLACE VELO");
			combo_typlace.setBounds(193,80,130,25);
			pn.add(combo_typlace);
			
			
			btenrg=new JButton("ENREGISTRER");
			btenrg.setBounds(55,140,120,25);
			btenrg.addActionListener(new ActionListener(){
			     public void actionPerformed(ActionEvent ev){
				String numero,type;
				numero=tfnum_place.getText();
				type=combo_typlace.getSelectedItem().toString();
				
				String rq1="insert into tb_place(num_place,type_place,disponible) values('"+numero+"','"+type+"','oui')";
				try{
					st=con.laConnection().createStatement();
					if(!numero.equals("")&&!type.equals("")){
					st.executeUpdate(rq1);
					JOptionPane.showMessageDialog(null,"Insertion reussie!",null,JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(null,"Completez le formulaire!",null,JOptionPane.ERROR_MESSAGE);
					}
				}
				catch(SQLException ex){
			    	JOptionPane.showMessageDialog(null,"Erreur!",null,JOptionPane.ERROR_MESSAGE);	
			    }
				dispose();
				Enregistrement eg=new Enregistrement();
				eg.setVisible(true);
			}

			});
					pn.add(btenrg);
			
			btsupp=new JButton("SUPPRIMER");
			btsupp.setBounds(200,140,120,25);
			btsupp.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ev){
					String numero=tfnum_place.getText();
			        String sql = "update tb_place set disponible='non' where num_place='" + numero + "'";
					try{
						st=con.laConnection().createStatement();
						if(JOptionPane.showConfirmDialog(null,"voulez vous Supprimer? ",null,JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){  
				          st.executeUpdate(sql);
				          JOptionPane.showMessageDialog(null,"Suppression effectuee avec succes !",null,JOptionPane.INFORMATION_MESSAGE);
			            }
					}
					catch(SQLException ex){
				    	JOptionPane.showMessageDialog(null,"Erreur!",null,JOptionPane.ERROR_MESSAGE);	
				    }
					dispose();
					Enregistrement eg=new Enregistrement();
					eg.setVisible(true);
				}
			});
			pn.add(btsupp);
			
			lbtitre2=new JLabel("Places disponibles");
			lbtitre2.setBounds(80,180,300,30);
			lbtitre2.setFont(new Font("Arial",Font.BOLD,22));
			pn.add(lbtitre2);
			
			
			btplace_dispo=new JButton("VERIFIER");
			btplace_dispo.setBounds(100,230,120,25);
			btplace_dispo.addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent ev){
					String type=combo_typlace.getSelectedItem().toString();
					DefaultTableModel df=new  DefaultTableModel();
					  init();
					  pn.add(scroll);
					 df.addColumn("Numero place");
					 df.addColumn("Type place");
					 table.setModel(df);
					 String rq="select * from tb_place where type_place='"+type+"' and disponible='oui' ";
					 try{
						 st=con.laConnection().createStatement();
						 rst=st.executeQuery(rq);
						 while(rst.next()){
							 df.addRow(new Object[]{
			rst.getString("num_place"),rst.getString("type_place")
									 });
							 
						 }
						 
						 
					 }
					 catch(SQLException ex){
					    	JOptionPane.showMessageDialog(null,"Erreur !",null,JOptionPane.ERROR_MESSAGE);	
					    }
			
				}
				
			});
			pn.add(btplace_dispo);
			
			DefaultTableModel df=new  DefaultTableModel();
			  init();
			  pn.add(scroll);
			 df.addColumn("Numero place");
			 df.addColumn("Type place");
			 table.setModel(df);
			 String rq="select * from tb_place where disponible='oui' ";
			 try{
				 st=con.laConnection().createStatement();
				 rst=st.executeQuery(rq);
				 while(rst.next()){
					 df.addRow(new Object[]{
	rst.getString("num_place"),rst.getString("type_place")
							 });}}
				 
			 catch(SQLException ex){
			    	JOptionPane.showMessageDialog(null,"Erreur !",null,JOptionPane.ERROR_MESSAGE);	
			    }
			 btactu=new JButton("ACTUALISER");
				btactu.setBounds(600,5,120,25);
				btactu.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ev){
						dispose();
						Enregistrement eg=new Enregistrement();
						eg.setVisible(true);
						
					}
				});
				pn.add(btactu);
	    }
			
			public static void main(String[] args) {
				Enregistrement enrg=new Enregistrement();
				enrg.setVisible(true);
				
			}
			 private void init(){
					table=new JTable();
					scroll=new JScrollPane();
					scroll.setBounds(400,40,460,270);
					scroll.setViewportView(table);
				}	
}