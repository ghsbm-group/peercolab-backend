package com.ghsbm.group.peer.colab.domain.file.core.ports.outgoing;

import com.ghsbm.group.peer.colab.domain.file.core.model.File;
//pt db
public interface FileRepository {
    File saveFile(File file);
}
