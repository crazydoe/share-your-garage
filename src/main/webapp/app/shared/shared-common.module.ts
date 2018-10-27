import { NgModule } from '@angular/core';

import { ShareYourGarageSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [ShareYourGarageSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ShareYourGarageSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ShareYourGarageSharedCommonModule {}
