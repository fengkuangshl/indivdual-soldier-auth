export const ACCEPT_CONFIG = {
  image: ['.png', '.jpg', '.jpeg', '.gif', '.bmp'],
  video: ['.mp4', '.rmvb', '.mkv', '.wmv', '.flv'],
  document: ['.doc', '.docx', '.xls', '.xlsx', '.ppt', '.pptx', '.pdf', '.txt', '.tif', '.tiff'],
  getAll: function (): Array<string> {
    return [...ACCEPT_CONFIG.image, ...ACCEPT_CONFIG.video, ...ACCEPT_CONFIG.document]
  }
}
