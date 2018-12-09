package gui;

import client.FileClient;
import client.PeerClient;
import manager.FilesListManager;
import manager.PeersListManager;
import property.FileStorageProperties;
import representation.File;
import representation.Peer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainFrameController {
    public static final int DEFAULT_PORT = 8080;
    public static final String DEFAULT_FOLDER_PATH = FileStorageProperties.getUploadDir();

    private int connectionPort;
    private String selectedFolderPath = DEFAULT_FOLDER_PATH;
    private List<File> fileList;
    private List<File> localFileList;
    private HashMap<File,List<Peer>> fileMap;
    private List<Peer> peerList;
    private File fileToDownload;
    private File fileToDelete;
    private File localFileToSend;
    private List<Peer> fileRecipients;
    private File localFileToDelete;
    private MainFrame mainframe;

    public MainFrameController() {
        initMainFrame();
        refreshFileList();
        refreshLocalFileList();
        refreshParametersLabels();
        refreshPeerList();
        runButtonListeners();
    }

    /**
     * Fonction d'inintialisation de tous les éléments de la fenetre
     */
    private void initMainFrame() {
        connectionPort = DEFAULT_PORT;
        selectedFolderPath = DEFAULT_FOLDER_PATH;
        //peerList = new ArrayList<>();
        peerList = PeersListManager.readPeers();
        fileList = new ArrayList<>();
        fileToDownload = null;
        mainframe = new MainFrame();
        mainframe.getDefaultPortParam().setText(Integer.toString(DEFAULT_PORT)); // default port, can't change during execution
    }

    /**
     * Fonction pour mettre à jour les champs de texte en fonction des paramètres du serveur
     */
    public void refreshParametersLabels() {
        mainframe.getSelectedFolder().setText(selectedFolderPath);
        mainframe.getPortParamField().setText(Integer.toString(connectionPort));
    }

    /**
     * Rafraichissement de la liste des fichiers disponibles ainsi que de son affichage
     */
    public void refreshFileList() {
        mainframe.getDownloadFileButton().setEnabled(false);
        mainframe.getDeleteFileButton().setEnabled(false);

        DefaultListModel<File> model = new DefaultListModel<>();
        mainframe.getFileList().setModel(model);

        //On récupère la map des fichiers disponibles afin de connaitre aussi les pairs qui ont ces fichiers
        fileMap = FilesListManager.getAvailableFiles();
        fileList = FilesListManager.getFileListFromMap(fileMap);

        //On les ajoute a l'affichage
        if(fileList != null) {
            for (File f : fileList) {
                model.addElement(f);
            }
        }
    }

    /**
     * Rafraichissement de la liste des fichiers locaux partagés
     */
    public void refreshLocalFileList() {
        mainframe.getDeleteLocalFileButton().setEnabled(false);
        mainframe.getRecipientsList().setEnabled(false);
        mainframe.getSendLocalFileButton().setEnabled(false);

        DefaultListModel<File> model = new DefaultListModel<>();
        mainframe.getLocalFilesList().setModel(model);

        //On lit le fichier de persistence contenant la liste des fichiers
        localFileList = FilesListManager.readFiles();

        //On ajoute les fichiers à l'affichage
        if(localFileList != null) {
            for (File f : localFileList) {
                model.addElement(f);
            }
        }
    }

    /**
     * Rafraichissement de la liste des pairs
     */
    public void refreshPeerList() {
        mainframe.getDeletePeerButton().setEnabled(false);
        mainframe.getDeleteLocalFileButton().setEnabled(false);
        mainframe.getSendLocalFileButton().setEnabled(false);

        DefaultListModel<Peer> model = new DefaultListModel<>();
        mainframe.getPeerList().setModel(model);
        mainframe.getRecipientsList().setModel(model);

        if(peerList != null) {
            for (Peer p : peerList) {
                model.addElement(p);
            }
        }
    }

    /**
     * Permet d'afficher du texte dans l'encadré supérieur
     * @param text texte à afficher
     */
    public void setInfoMessage(String text) {
        mainframe.getInfoMessage().setText(text);
    }

    public void runButtonListeners() {
        /*------------------ Download Tab Listeners --------------------*/

        /* Listener for file list refresh button */
        mainframe.getRefreshFileListButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //On rafraichit toutes les listes et on affiche un message
                refreshFileList();
                refreshLocalFileList();
                refreshPeerList();
                setInfoMessage("INFO: Liste des fichiers disponibles à jour");
            }
        });

        /* Listener for file list selection */
        mainframe.getFileList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(mainframe.getFileList().getSelectedValue() != null) {
                    mainframe.getDownloadFileButton().setEnabled(true);
                    mainframe.getDeleteFileButton().setEnabled(true);
                }
            }
        });

        /* Listener for download file button */
        mainframe.getDownloadFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileToDelete = null;
                fileToDownload = (File) mainframe.getFileList().getSelectedValue(); //récupération du fichier selectionné

                //Si le fichier est bien téléchargé
                if(FileClient.getFile(fileMap.get(fileToDownload).get(0).getUrl(),fileToDownload.getFileId()))
                    setInfoMessage("INFO: Fichier téléchargé");
                //Si une erreur est survenue
                else
                    setInfoMessage("ERREUR: Un problème est survenu lors du téléchargement du fichier");

                //On rafraichit les listes de fichiers
                refreshLocalFileList();
                refreshFileList();

                displayFileActionConsole();
            }
        });

        /* Listener for delete file button */
        mainframe.getDeleteFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileToDownload = null;
                fileToDelete = (File) mainframe.getFileList().getSelectedValue(); //On récupère le fichier selectionné

                //On récupère la liste des pairs qui partagent ce fichier grâce à la map
                List<Peer> peers = fileMap.get(fileToDelete);
                //On supprime le fichier sur les pairs disponibles
                for (Peer p: peers){
                    FileClient.deleteFile(p.getUrl(),fileToDelete.getFileId());
                }

                setInfoMessage("INFO: Le fichier a été supprimé sur les pairs disponibles");

                //On rafraichit les listes des fichiers
                refreshFileList();
                refreshLocalFileList();

                displayFileActionConsole();
            }
        });

        /*------------------ Share Tab Listeners --------------------*/

        /* Listener for local file list selection */
        mainframe.getLocalFilesList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(mainframe.getLocalFilesList().getSelectedValue() != null){
                    mainframe.getDeleteLocalFileButton().setEnabled(true);
                    mainframe.getRecipientsList().setEnabled(true);
                }
            }
        });

        /* Listener for delete local file button */
        mainframe.getDeleteLocalFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                localFileToSend = null;
                fileRecipients = null;
                localFileToDelete = (File) mainframe.getLocalFilesList().getSelectedValue(); //On récupère le ficher selectionné
                FilesListManager.deleteLocalFile(localFileToDelete.getFileId()); //On supprime le fichier localement

                setInfoMessage("INFO: Le fichier a été supprimé du dossier de partage");

                //On rafraichit les listes des fichiers
                refreshFileList();
                refreshLocalFileList();

                displayLocalFileActionConsole();
            }
        });

        /* Listener for recipient list selection */
        mainframe.getRecipientsList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(mainframe.getRecipientsList().getSelectedValue() != null) {
                    mainframe.getSendLocalFileButton().setEnabled(true);
                }
            }
        });

        /* Listener for send local file confirm button */
        mainframe.getSendLocalFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                localFileToDelete = null;
                localFileToSend = (File) mainframe.getLocalFilesList().getSelectedValue(); //On récupère le fichier selectionné
                fileRecipients = (List<Peer>) mainframe.getRecipientsList().getSelectedValuesList(); //On récupère les pairs selectionnés
                for (Peer p: fileRecipients
                     ) {
                    try {
                        if(!FileClient.uploadFile(p.getUrl(),localFileToSend.getFileId())) //Si le fichier n'a pas été upload
                            setInfoMessage("ERREUR: Un problème est survenu lors de l'ajout du fichier sur le pair");
                        else
                            setInfoMessage("INFO: Le fichier a bien été envoyé");

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                displayLocalFileActionConsole();
            }
        });


        /*----------------- Parameters Tab Listeners --------------------*/

        /* Listener for folder button selection */
        mainframe.getSelectFolderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainframe.openFileChooser();
                //Si un dossier a été selectionné, on change le répertoire partagé et on reset le champ
                if(mainframe.getSelectedFolder().getText() != null) {
                    selectedFolderPath = mainframe.getSelectedFolder().getText();
                    setInfoMessage("INFO: Le répertoire partagé a été modifié");
                }
                refreshParametersLabels(); //on rafraichit la fenetre avec les nouveaux parametres
                displayParamsConsole();
            }
        });

        /* Listener for peer list selection */
        mainframe.getPeerList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(mainframe.getPeerList().getSelectedValue() != null) {
                    mainframe.getDeletePeerButton().setEnabled(true);
                }
            }
        });

        /* Listener for peer add button */
        mainframe.getAddPeerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String peerUrl = mainframe.getPeerParamField().getText(); //on récupère l'url entrée
                if(!mainframe.getPeerParamField().getText().isEmpty()) {
                    if(PeersListManager.isUrlValid(peerUrl)){ //on vérifie l'integrité de l'URL entrée
                        if(PeerClient.registerPeers(peerUrl)){
                            peerList.add(new Peer(peerUrl)); //on ajoute l'url à la liste des pairs
                            PeersListManager.savePeers(peerList); //on sauvegarde dans le fichier de persistence
                            setInfoMessage("INFO: Le pair a bien été ajouté");
                            refreshPeerList();
                        }
                        else{
                            setInfoMessage("ERREUR: Le pair n'est pas disponible");
                        }

                        displayParamsConsole();
                    }
                    else
                        setInfoMessage("ERREUR: Vous devez entrer une URL conforme (http://xx.xx.xx.xx:port)");

                    mainframe.getPeerParamField().setText("");
                }
            }
        });

        /* Listener for peer delete button */
        mainframe.getDeletePeerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JList pl = mainframe.getPeerList();
                Peer p = (Peer)pl.getSelectedValue();
                if(PeerClient.unregisterPeer(p.getUrl())){
                    peerList.remove(pl.getSelectedValue());
                    ((DefaultListModel) pl.getModel()).remove(pl.getSelectedIndex());
                    PeersListManager.savePeers(peerList);
                    setInfoMessage("INFO: Pair supprimé");
                    refreshPeerList();
                }
                else{
                    setInfoMessage("ERREUR: Le pair n'est pas disponible, essayez plus tard");
                }

                displayParamsConsole();
            }
        });

        /* Listener for save parameters button */
        mainframe.getSavePortParamButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!mainframe.getPortParamField().getText().isEmpty()) {
                    try {
                        connectionPort = Integer.parseInt(mainframe.getPortParamField().getText());
                    } catch (NumberFormatException ex) {
                        connectionPort = DEFAULT_PORT;
                    }
                }

                refreshParametersLabels();
                refreshPeerList();
                displayParamsConsole();
            }
        });
    }

    public void displayFileActionConsole() {
        System.out.println("--- File action detected ---");
        System.out.println("File to download : " + fileToDownload);
        System.out.println("File to delete : " + fileToDelete);
    }

    public void displayLocalFileActionConsole() {
        System.out.println("--- Local file action detected ---");
        System.out.println("Local file to send : " + localFileToSend);
        System.out.println("Local file recipient(s) : " + fileRecipients);
        System.out.println("Local file to delete : " + localFileToDelete);
    }

    public void displayParamsConsole() {
        System.out.println("--- Parameters changes save ---");
        System.out.println("Connection port : " + connectionPort);
        System.out.println("Selected folder : " + selectedFolderPath);
        System.out.println("List of known peers : " + peerList.toString());
    }

    public List<File> getFileList() { return fileList; }

    public void setFileList(List<File> fileList) { this.fileList = fileList; }

    public List<File> getLocalFileList() { return localFileList; }

    public void setLocalFileList(List<File> localFileList) { this.localFileList = localFileList; }

    public List<Peer> getPeerList() { return peerList; }

    public void setPeerList(List<Peer> peerList) { this.peerList = peerList; }

    public File getFileToDownload() { return fileToDownload; }

    public void setFileToDownload(File fileToDownload) { this.fileToDownload = fileToDownload; }

    public File getFileToDelete() { return fileToDelete; }

    public void setFileToDelete(File fileToDelete) { this.fileToDelete = fileToDelete; }

    public File getLocalFileToSend() { return localFileToSend; }

    public void setLocalFileToSend(File localFileToSend) { this.localFileToSend = localFileToSend; }

    public File getLocalFileToDelete() { return localFileToDelete; }

    public void setLocalFileToDelete(File localFileToDelete) { this.localFileToDelete = localFileToDelete; }

    public List<Peer> getFileRecipients() { return fileRecipients; }

    public void setFileRecipients(List<Peer> fileRecipients) { this.fileRecipients = fileRecipients; }

    public HashMap<File, List<Peer>> getFileMap() {
        return fileMap;
    }

    public void setFileMap(HashMap<File, List<Peer>> fileMap) {
        this.fileMap = fileMap;
    }
}
