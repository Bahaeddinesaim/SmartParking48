package parking_;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;

public class Enregistrement extends JFrame {

    private static final long serialVersionUID = 1L;

    Statement st;
    Conneccion con=new Conneccion();
    ResultSet rst;
    JTable table,table2;
    JScrollPane scroll,scroll2;
    JLabel lbtitre1,lbtitre2,lbnum_place,lbvelo,lbmoto,lbemail,lbvoi,lbtype_place,lb_idproprio,lb_nomproprio,lb_mtype,lb_place,lb_matricule,lbplaces_voiture_occup,lbplaces_velo_occup,lbplaces_moto_occup;
    JTextField tfnum_place,tf_idproprio,tf_nomproprio,tf_place,tf_matricule,tfemail;
    JComboBox combo_typlace,combo_typlace2;
    JButton btenrg,btsupp,btplace_dispo,btactu,btenrg_occup,btrech,btliberer_place,btmotdepasseAleatoire;



    public Enregistrement(){
        if (!AuthenticationPage.isConnected) {
            dispose(); // Fermer la fenêtre actuelle
            AuthenticationPage login = new AuthenticationPage();
            login.setVisible(true);
        } else {
            // Initialiser la fenêtre d'enregistrement normalement
            setTitle("Parking48");
            setSize(950, 800);
            setLocationRelativeTo(null);
            setResizable(false);
            final JPanel pn=new JPanel();
            pn.setLayout(null);
            getContentPane().add(pn);
            pn.setBackground(new Color(180,200,240));


            lbtitre1=new JLabel("Formulaire d'enregistrement des places");
            lbtitre1.setBounds(50,10,460,30);
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

            ImageIcon save = new ImageIcon(getClass().getResource("save.png"));
            btenrg=new JButton("ENREGISTRER",save);
            btenrg.setBounds(15,140,160,25);
            btenrg.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev){
                    String numero,type;
                    numero=tfnum_place.getText();
                    type=combo_typlace.getSelectedItem().toString();

                    // Vérifier si le numéro de place est déjà présent dans la base de données
                    String rq_check = "SELECT * FROM tb_place WHERE num_place = '" + numero + "'";
                    try {
                        st = con.laConnection().createStatement();
                        ResultSet rs = st.executeQuery(rq_check);
                        if(rs.next()) {
                            // Si la place existe déjà, mettre à jour la disponibilité à "oui"
                            String rq_update = "UPDATE tb_place SET disponible = 'oui' WHERE num_place = '" + numero + "'";
                            st.executeUpdate(rq_update);
                            JOptionPane.showMessageDialog(null, "Place mise à jour avec succès!", null, JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // Si la place n'existe pas, l'ajouter avec la disponibilité "oui"
                            String rq_insert = "INSERT INTO tb_place(num_place, type_place, disponible) VALUES('" + numero + "','" + type + "','oui')";
                            st.executeUpdate(rq_insert);
                            JOptionPane.showMessageDialog(null, "Insertion réussie!", null, JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch(SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erreur!", null, JOptionPane.ERROR_MESSAGE);
                    }
                    dispose();
                    Enregistrement eg=new Enregistrement();
                    eg.setVisible(true);
                }
            });
            pn.add(btenrg);

            ImageIcon supp = new ImageIcon(getClass().getResource("supp.png"));
            btsupp=new JButton("SUPPRIMER",supp);
            btsupp.setBounds(200,140,160,25);
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



            ImageIcon ver = new ImageIcon(getClass().getResource("ver.png"));
            btplace_dispo=new JButton("VERIFIER PLACE DISPO",ver);
            btplace_dispo.setBounds(66,180,238,25);
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
            ImageIcon icon = new ImageIcon(getClass().getResource("ref.png"));
            btactu = new JButton("ACTUALISER", icon);
            btactu.setBounds(516,5,164,25);
            btactu.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev){
                    dispose();
                    Enregistrement eg=new Enregistrement();
                    eg.setVisible(true);
                }
            });
            pn.add(btactu);
            lbtitre2=new JLabel("Gérer Client");
            lbtitre2.setBounds(80,260,300,30);
            lbtitre2.setFont(new Font("Arial",Font.BOLD,22));
            pn.add(lbtitre2);

            lb_idproprio=new JLabel("Numero du proprietaire (TEL ou CIN) ");
            lb_idproprio.setBounds(15,323,300,25);
            pn.add(lb_idproprio);
            tf_idproprio=new JTextField();
            tf_idproprio.setBounds(289,323,80,25);
            pn.add(tf_idproprio);

            lb_nomproprio=new JLabel("Nom du proprietaire");
            lb_nomproprio.setBounds(15,355,250,25);
            pn.add(lb_nomproprio);

            tf_nomproprio=new JTextField();
            tf_nomproprio.setBounds(240,358,130,25);
            pn.add(tf_nomproprio);

            lb_mtype=new JLabel("Type de moyen de transport");
            lb_mtype.setBounds(15,390,250,25);
            pn.add(lb_mtype);

            combo_typlace2=new JComboBox();
            combo_typlace2.addItem("");
            combo_typlace2.addItem("VOITURE");
            combo_typlace2.addItem("MOTO");
            combo_typlace2.addItem("VELO");
            combo_typlace2.setBounds(240,390,130,25);
            pn.add(combo_typlace2);
            //Numero de place
            lb_place=new JLabel("Numero de place");
            lb_place.setBounds(15,420,250,25);
            pn.add(lb_place);

            tf_place=new JTextField();
            tf_place.setBounds(240,420,130,25);
            pn.add(tf_place);
            //Numero d'immatriculation
            lb_matricule=new JLabel("Numero d'immatriculation");
            lb_matricule.setBounds(15,455,250,25);
            pn.add(lb_matricule);

            tf_matricule=new JTextField();
            tf_matricule.setBounds(240,455,130,25);
            pn.add(tf_matricule);

            lbemail = new JLabel("Entrez l'adresse e-mail :");
            lbemail.setBounds(15,489,250,25);
            pn.add(lbemail);

            tfemail = new JTextField();
            tfemail.setBounds(240,489,130,25);
            pn.add(tfemail);

            btenrg_occup=new JButton("AJOUTER",save);
            btenrg_occup.setBounds(20,526,158,25);
            btenrg_occup.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev){
                    String   idproprio,nomproprio,typemt,num_place,matricule;
                    idproprio=tf_idproprio.getText();
                    nomproprio=tf_nomproprio.getText();
                    typemt=combo_typlace2.getSelectedItem().toString();
                    matricule=tf_matricule.getText();
                    num_place=tf_place.getText();
                    String email = tfemail.getText();
                    String randomPassword = generateRandomPassword();
                    sendEmail(email,randomPassword);
                    String rq="insert into tb_occupation(proprio_id,proprio_nom,mt_type,mt_matricule,id_place,email,randomPassword)"
                            + "values('"+idproprio+"','"+nomproprio+"','"+typemt+"','"+matricule+"','"+num_place+"','"+email+"','"+randomPassword+"')";
                    String rq2="update tb_place set disponible='non' where num_place='"+num_place+"'";

                    try{
                        st=con.laConnection().createStatement();
                        if(!idproprio.equals("")&&!nomproprio.equals("")&&!typemt.equals("")&&!num_place.equals("")&&!email.equals("")&&!randomPassword.equals("")){
                            st.executeUpdate(rq);
                            st.executeUpdate(rq2);
                            JOptionPane.showMessageDialog(null,"Occupation enregistree avec succes !",null,JOptionPane.INFORMATION_MESSAGE);
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

                public void sendEmail(String to, String password) {
                    final String username = "haythamhammouda123@gmail.com";
                    final String userpassword = "wazycepfsfiqfzya";
                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");
                    props.put("mail.smtp.ssl.protocols", "TLSv1.2 TLSv1.3");
                    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");


                    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, userpassword);
                        }
                    });
                    try {
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(username));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                        message.setSubject("Votre mot de passe");
                        message.setText("Voici votre mot de passe  : " + password);

                        Transport.send(message);

                        JOptionPane.showMessageDialog(null, "Email sent successfully!", null, JOptionPane.INFORMATION_MESSAGE);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        String errorMessage = "Failed to send email: " + e.getMessage();
                        if (e.getNextException() != null) {
                            errorMessage += "\nNext Exception: " + e.getNextException().getMessage();
                        }
                        JOptionPane.showMessageDialog(null, "Failed to send email:!", null, JOptionPane.ERROR_MESSAGE);


                    }
                }
                private String generateRandomPassword() {
                    // Définition des caractères pouvant être utilisés dans le mot de passe
                    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";

                    // Longueur du mot de passe désirée
                    int length = 10;

                    // Génération du mot de passe aléatoire
                    Random random = new Random();
                    StringBuilder password = new StringBuilder();
                    for (int i = 0; i < length; i++) {
                        password.append(chars.charAt(random.nextInt(chars.length())));
                    }

                    return password.toString();
                }
            });
            pn.add(btenrg_occup);

            ImageIcon cher = new ImageIcon(getClass().getResource("cher.png"));
            btrech=new JButton("CHERCHER",cher);
            btrech.setBounds(210,526,158,25);
            btrech.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev){
                    String num_place=tf_place.getText();
                    String rq="select * from tb_occupation where id_place='"+num_place+"'";
                    try{
                        st=con.laConnection().createStatement();
                        rst=st.executeQuery(rq);
                        if(rst.next()){
                            tf_idproprio.setText(rst.getString("proprio_id"));
                            tf_nomproprio.setText(rst.getString("proprio_nom"));
                            combo_typlace2.setSelectedItem(rst.getString("mt_type"));
                            tf_place.setText(rst.getString("id_place"));
                            tf_matricule.setText(rst.getString("mt_matricule"));
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Enregistrement inexistant!",null,JOptionPane.ERROR_MESSAGE);
                        }


                    }
                    catch(SQLException ex){
                        JOptionPane.showMessageDialog(null,"Erreur!",null,JOptionPane.ERROR_MESSAGE);
                    }

                }
            });
            pn.add(btrech);



            btliberer_place=new JButton("LIBERER",supp);
            btliberer_place.setBounds(20,566,158,25);
            btliberer_place.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev){
                    String num_place=tf_place.getText();
                    String rq1="update tb_place set disponible='oui' where num_place='"+num_place+"'",
                            rq2="delete from tb_occupation where id_place='"+num_place+"'";
                    try{
                        st=con.laConnection().createStatement();
                        if(JOptionPane.showConfirmDialog(null,"voulez-vous liberer cette place? ",null,JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
                            st.executeUpdate(rq1);
                            st.executeUpdate(rq2);
                            JOptionPane.showMessageDialog(null,"Liberation de place confirmee !",null,JOptionPane.INFORMATION_MESSAGE);
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
            pn.add(btliberer_place);

            DefaultTableModel df2=new  DefaultTableModel();
            init2();
            pn.add(scroll2);
            df2.addColumn("Num_place");
            df2.addColumn("Num_proprio");
            df2.addColumn("Nom_proprio");
            df2.addColumn("Type moyen de transport");
            df2.addColumn("Numero d'immatriculation");
            df2.addColumn("Email ");
            table2.setModel(df2);
            String rq2="select * from tb_occupation ";
            try{
                st=con.laConnection().createStatement();
                rst=st.executeQuery(rq2);
                while(rst.next()){
                    df2.addRow(new Object[]{
                            rst.getString("id_place"),rst.getString("proprio_id"),rst.getString("proprio_nom"),rst.getString("mt_type")
                            ,rst.getString("mt_matricule"),rst.getString("email")
                    });

                }


            }

            catch(SQLException ex){
                JOptionPane.showMessageDialog(null,"Erreur !",null,JOptionPane.ERROR_MESSAGE);
            }

            // btmotdepasseAleatoire = new JButton("GÉNÉRER MDP");
            //  btmotdepasseAleatoire.setBounds(210, 566, 158, 25);
            // btmotdepasseAleatoire.addActionListener(new ActionListener() {
            //   public void actionPerformed(ActionEvent ev) {
            //     String email = tfemail.getText();
            //   String randomPassword = generateRandomPassword();

            //}



            //	 private String generateRandomPassword() {
            // Définition des caractères pouvant être utilisés dans le mot de passe
            //        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";

            // Longueur du mot de passe désirée
            //      int length = 10;

            // Génération du mot de passe aléatoire
            //    Random random = new Random();
            //  StringBuilder password = new StringBuilder();
            // for (int i = 0; i < length; i++) {
            //   password.append(chars.charAt(random.nextInt(chars.length())));
            //}

            //return password.toString();
            //}
            //});
            //pn.add(btmotdepasseAleatoire);

            ImageIcon voi = new ImageIcon(getClass().getResource("voi.png"));
            lbvoi=new JLabel(voi);
            lbvoi.setBounds(80,601,153,78);
            pn.add(lbvoi);

            lbplaces_voiture_occup=new JLabel("");
            lbplaces_voiture_occup.setBounds(30,657,500,43);
            pn.add(lbplaces_voiture_occup);

            String req1="select count(*) as nbvoitures from tb_occupation where mt_type='VOITURE'";
            try{
                st=con.laConnection().createStatement();
                rst=st.executeQuery(req1);
                if(rst.next()){

                    lbplaces_voiture_occup.setText("Nombre total des places voitures occupees : "+rst.getString("nbvoitures"));
                }

            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null,"Erreur !",null,JOptionPane.ERROR_MESSAGE);
            }

            ImageIcon velo = new ImageIcon(getClass().getResource("velo.png"));
            lbvelo=new JLabel(velo);
            lbvelo.setBounds(386,600,153,79);
            pn.add(lbvelo);

            lbplaces_velo_occup=new JLabel("");
            lbplaces_velo_occup.setBounds(335,657,500,43);
            pn.add(lbplaces_velo_occup);

            String req2="select count(*) as nbvelos from tb_occupation where mt_type='VELO'";
            try{
                st=con.laConnection().createStatement();
                rst=st.executeQuery(req2);
                if(rst.next()){

                    lbplaces_velo_occup.setText("Nombre total des places velos occupees : "+rst.getString("nbvelos"));
                }

            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null,"Erreur !",null,JOptionPane.ERROR_MESSAGE);
            }


            ImageIcon moto = new ImageIcon(getClass().getResource("moto.png"));
            lbmoto=new JLabel(moto);
            lbmoto.setBounds(678,600,153,78);
            pn.add(lbmoto);

            lbplaces_moto_occup=new JLabel("");
            lbplaces_moto_occup.setBounds(631,657,500,43);
            pn.add(lbplaces_moto_occup);

            ImageIcon deco = new ImageIcon(getClass().getResource("deco.png"));
            JButton dex = new JButton("Deconexion ",deco);
            dex.setBounds(690, 5, 170, 26);
            dex.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev){
                    dispose();

                }
            });
            pn.add(dex);

            String req3="select count(*) as nbmotos from tb_occupation where mt_type='MOTO'";
            try{
                st=con.laConnection().createStatement();
                rst=st.executeQuery(req3);
                if(rst.next()){

                    lbplaces_moto_occup.setText("Nombre total des places motos occupees : "+rst.getString("nbmotos"));
                }

            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null,"Erreur !",null,JOptionPane.ERROR_MESSAGE);
            }



        }}
    public static void main(String[] args) throws IOException {
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
        AuthenticationPage login = new AuthenticationPage();
        login.setVisible(true);

    }
    private void init(){
        table=new JTable();
        scroll=new JScrollPane();
        scroll.setBounds(400,40,460,270);
        scroll.setViewportView(table);
    }

    private void init2(){
        table2=new JTable();
        scroll2=new JScrollPane();
        scroll2.setBounds(400,370,460,220);
        scroll2.setViewportView(table2);

    }
}
