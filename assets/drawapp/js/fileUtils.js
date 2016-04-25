function saveCanvasToFile(){
	if( supports_html5_storage() == true )
	{
	   localStorage.setItem("myItem", "myData");
	   var myDataString = localStorage.getItem("myItem");
	   console.log("myDataString ="+myDataString);
	   alert(myDataString);
	}else{
		console.log("myDataString = false");
		   
	}
}

function supports_html5_storage()
{
      try
      {
        return 'localStorage' in window && window['localStorage'] !== null;
      }
      catch (e)
      {
        return false;
      }
}

function filetransfer(download_link, fp) {
var fileTransfer = new FileTransfer();
// File download function with URL and local path
fileTransfer.download(download_link, fp,
                    function (entry) {
                        alert("download complete: " + entry.fullPath);
                    },
                 function (error) {
                     //Download abort errors or download failed errors
                     alert("download error source " + error.source);
                     //alert("download error target " + error.target);
                     //alert("upload error code" + error.code);
                 }
            );
}


//First step check parameters mismatch and checking network connection if available call    download function
function DownloadFile(URL, Folder_Name, File_Name) {
//Parameters mismatch check
if (URL == null && Folder_Name == null && File_Name == null) {
    return;
}
else {
    //checking Internet connection availablity
    var networkState = navigator.connection.type;
    if (networkState == Connection.NONE) {
        return;
    } else {
        download(URL, Folder_Name, File_Name); //If available download function call
    }
  }
}

function download(URL, Folder_Name, File_Name) {
	// step to request a file system
	window.requestFileSystem(LocalFileSystem.PERSISTENT, 0, fileSystemSuccess,
			fileSystemFail);

	function fileSystemSuccess(fileSystem) {
		var download_link = encodeURI(URL);
		// Get extension of URL
		ext = download_link.substr(download_link.lastIndexOf('.') + 1);

		// to get root path of directory
		var directoryEntry = fileSystem.root;
		directoryEntry.getDirectory(Folder_Name, {
			create : true,
			exclusive : false
		}, onDirectorySuccess, onDirectoryFail); // creating folder in sdcard
		var rootdir = fileSystem.root;

		// Returns Fulpath of local directory
		var fp = rootdir.fullPath;
		// fullpath and name of the file which we want to give

		fp = fp + "/" + Folder_Name + "/" + File_Name + "." + ext;
		// download function call
		filetransfer(download_link, fp);
	}

	function onDirectorySuccess(parent) {
		// Directory created successfuly
	}

	function onDirectoryFail(error) {
		// Error while creating directory
		alert("Unable to create new directory: " + error.code);
	}

	function fileSystemFail(evt) {
		// Unable to access file system
		alert(evt.target.error.code);
	}
}


